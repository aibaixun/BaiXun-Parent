package com.aibaixun.common.redis.lock;

import com.aibaixun.basic.lock.BaseLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis lock
 * @author wangxiao@aibaixun.com
 * @date 2022/1/3
 */
@Component
@ConditionalOnProperty(prefix = "bx.lock", name = "type", havingValue = "redis")
public class RedisDistributedLock extends BaseLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    protected final ThreadLocal<String> lockFlag = new ThreadLocal<>();

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String UNLOCK_LUA =  "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
            "then " +
            "    return redis.call(\"del\",KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end ";;


    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = getRedisLock(key, expire);
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.debug("get redisDistributeLock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                logger.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            result = getRedisLock(key, expire);
        }
        return result;
    }


    public Boolean getRedisLock (String key,long expire) {
        String lockKey = generatorLockKey(key);
        String value = UUID.randomUUID().toString();
        lockFlag.set(value);
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value, expire,TimeUnit.SECONDS);
    }

    @Override
    public boolean releaseLock(String key) {
        String lockKey = generatorLockKey(key);
        String value = lockFlag.get();
        RedisScript<Long> redisScript = new DefaultRedisScript<>(UNLOCK_LUA, Long.class);
        Long result;
        try {
            result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value);
        }finally {
            lockFlag.remove();
        }
        return result!= null && result>0;
    }
}

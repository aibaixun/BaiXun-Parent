package com.aibaixun.cache.redis;

import com.aibaixun.cache.CacheProperties;
import com.aibaixun.basic.cache.CacheService;
import com.aibaixun.common.util.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class RedisCache implements CacheService {

    private final StringRedisTemplate template;
    private final CacheProperties properties;

    public RedisCache(StringRedisTemplate template, CacheProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public <R> R get(String prefix, String key, Class<R> returnClass) {
        String kp = prefix + key;
        String strCache = (String)this.template.opsForValue().get(kp);
        if (strCache == null) {
            return null;
        } else {
            R r = JsonUtil.toObj(strCache, returnClass);
            return r;
        }
    }

    public <R> void put(String prefix, String key, Object value, Duration expiredTime) {
        if (value != null) {
            String kp = prefix + key;
            this.template.opsForValue().set(kp, JsonUtil.toJson(value), this.properties.getSecondCacheExpiredSeconds(), TimeUnit.SECONDS);
        }
    }

    public void evict(String prefix, String key) {
        String kp = prefix + key;
        this.template.delete(kp);
    }

    public Duration getExpireTime() {
        return Duration.ofSeconds(this.properties.getSecondCacheExpiredSeconds());
    }

}

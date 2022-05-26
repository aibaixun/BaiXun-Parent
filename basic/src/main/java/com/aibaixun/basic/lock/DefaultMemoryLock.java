package com.aibaixun.basic.lock;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/3
 */
public class DefaultMemoryLock extends BaseLock{

    private final Map<String,String> memoryMap = new ConcurrentHashMap<>();

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        boolean result = getMemoryLock(key);
        while ((!result) && retryTimes-- > 0) {
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            result = getMemoryLock(key);
        }
        return result;
    }


    public Boolean getMemoryLock(String key) {
        String lockKey = generatorLockKey(key);
        String value = UUID.randomUUID().toString();
        String oldValue =memoryMap.get(lockKey);
        if (oldValue==null){
            memoryMap.put(lockKey,value);
            return true;
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
       return memoryMap.remove(generatorLockKey(key))!=null;
    }
}

package com.aibaixun.basic.lock;


/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
public abstract class BaseLock implements Lock {

    protected static final String LOCK_PREFIX = "bx-lock:";


    @Override
    public boolean lock(String key) {
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes, long sleepMillis) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
    }

    @Override
    public boolean lock(String key, long expire) {
        return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes) {
        return lock(key, expire, retryTimes, SLEEP_MILLIS);
    }

    /**
     * 生成加锁key
     * @param key --  sourceKey
     * @return String
     */
    public String generatorLockKey(String key) {
        return LOCK_PREFIX+key;
    };

}

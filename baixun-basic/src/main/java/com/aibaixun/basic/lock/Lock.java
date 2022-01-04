package com.aibaixun.basic.lock;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
public interface Lock {


    long TIMEOUT_MILLIS = 5000;


    int RETRY_TIMES = 100;


    long SLEEP_MILLIS = 100;

    /**
     * 获取锁
     * @param key key
     * @return 成功/失败
     */
    boolean lock(String key);

    /**
     * 获取锁
     * @param key        key
     * @param retryCounter 重试次数
     * @return 成功/失败
     */
    boolean lock(String key, int retryCounter);

    /**
     * 获取锁
     * @param key         key
     * @param retryCounter  重试次数
     * @param sleepMillis 获取锁失败的重试间隔
     * @return 成功/失败
     */
    boolean lock(String key, int retryCounter, long sleepMillis);

    /**
     * 获取锁
     * @param key    key
     * @param expire 获取锁超时时间
     * @return 成功/失败
     */
    boolean lock(String key, long expire);

    /**
     * 获取锁
     * @param key        key
     * @param expire     获取锁超时时间
     * @param retryCounter 重试次数
     * @return 成功/失败
     */
    boolean lock(String key, long expire, int retryCounter);

    /**
     * 获取锁
     * @param key         key
     * @param expire      获取锁超时时间
     * @param retryCounter  重试次数
     * @param sleepMillis 获取锁失败的重试间隔
     * @return 成功/失败
     */
    boolean lock(String key, long expire, int retryCounter, long sleepMillis);

    /**
     * 释放锁
     * @param key key值
     * @return 释放结果
     */
    boolean releaseLock(String key);
}

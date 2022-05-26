package com.aibaixun.basic.cache;

import java.time.Duration;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public interface CacheService {

    <R> R get(String keyPrx, String key, Class<R> var3);

    <R> void put(String keyPrx, String key, Object value, Duration var4);

    void evict(String var1, String var2);

    Duration getExpireTime();
}

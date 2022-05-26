package com.aibaixun.cache.caffine;

import com.aibaixun.cache.CacheProperties;
import com.aibaixun.basic.cache.CacheService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;


import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class CaffeineCache implements CacheService {

    private final ConcurrentHashMap<String, Cache<String, Object>> cacheMap = new ConcurrentHashMap();
    private final CacheProperties properties;

    public CaffeineCache(CacheProperties properties) {
        this.properties = properties;
    }

    public <R> R get(String prefix, String key, Class<R> returnClass) {
        if (!this.cacheMap.containsKey(prefix)) {
            return null;
        } else {
            Cache<String, Object> localCache = this.cacheMap.get(prefix);
            String kp = prefix + key;
            Object r = localCache.getIfPresent(kp);
            return r != null && returnClass.isAssignableFrom(r.getClass()) ? (R) r : null;
        }
    }

    public <R> void put(String prefix, String key, Object value, Duration expiredTime) {
        Cache localCache;
        if (!this.cacheMap.containsKey(prefix)) {
            localCache = Caffeine.newBuilder().maximumSize(this.properties.getFirstCacheMaximumSize()).expireAfterWrite(this.properties.getFirstCacheExpiredSeconds(), TimeUnit.SECONDS).build();
            this.cacheMap.put(prefix, localCache);
        }

        localCache = this.cacheMap.get(prefix);
        String kp = prefix + key;
        localCache.put(kp, value);
    }

    public void evict(String prefix, String key) {
        if (this.cacheMap.containsKey(prefix)) {
            Cache<String, Object> localCache = this.cacheMap.get(prefix);
            String kp = prefix + key;
            localCache.invalidate(kp);
        }
    }

    public Duration getExpireTime() {
        return Duration.ofSeconds(this.properties.getFirstCacheExpiredSeconds());
    }

}

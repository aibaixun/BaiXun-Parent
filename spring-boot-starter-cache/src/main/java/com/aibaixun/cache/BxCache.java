package com.aibaixun.cache;

import com.aibaixun.basic.cache.CacheService;
import com.aibaixun.basic.cache.CacheUtils;
import com.aibaixun.cache.caffine.CaffeineCache;
import com.aibaixun.cache.redis.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class BxCache {

    private final CacheUtils cacheUtils;
    private final CaffeineCache firstCache;
    private final RedisCache secondCache;

    public BxCache(StringRedisTemplate template, CacheProperties properties) {
        CacheUtils.Builder builder = CacheUtils.newBuilder();
        this.firstCache = new CaffeineCache(properties);
        this.secondCache = new RedisCache(template, properties);
        builder.setCacheServices(new CacheService[]{this.firstCache, this.secondCache});
        this.cacheUtils = builder.build();
    }

    public <R> R get(String prefix, String key, Class<R> returnClass) {
        return this.cacheUtils.get(prefix, key, returnClass);
    }

    public <R> R get(String prefix, String key, Class<R> returnClass, Function<String, R> invokeOrigin) {
        return this.cacheUtils.get(prefix, key, returnClass, invokeOrigin);
    }

    public <R> R get(String prefix, String key, Class<R> returnClass, Function<String, R> invokeOrigin, boolean protect, Duration timeout) {
        return this.cacheUtils.get(prefix, key, returnClass, invokeOrigin, protect, timeout);
    }

    public CaffeineCache getFirstCache() {
        return this.firstCache;
    }

    public RedisCache getSecondCache() {
        return this.secondCache;
    }
}

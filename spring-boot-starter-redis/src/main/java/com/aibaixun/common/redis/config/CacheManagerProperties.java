package com.aibaixun.common.redis.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author mall
 * @date 2019/1/6
 */
@Configuration
@ConfigurationProperties(prefix = "bx.cache")
public class CacheManagerProperties {

    private List<CacheConfig> cacheManages;


    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getSecond() {
            return second;
        }

        public void setSecond(long second) {
            this.second = second;
        }
    }

    public List<CacheConfig> getCacheManages() {
        return cacheManages;
    }

    public void setCacheManages(List<CacheConfig> cacheManages) {
        this.cacheManages = cacheManages;
    }
}

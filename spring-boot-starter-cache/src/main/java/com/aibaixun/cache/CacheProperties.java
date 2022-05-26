package com.aibaixun.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@ConfigurationProperties(
        prefix = "bx.cache"
)
public class CacheProperties {

    private boolean enable = false;
    private long firstCacheExpiredSeconds = 900L;
    private long firstCacheMaximumSize = 1000L;
    private long secondCacheExpiredSeconds = 3600L;

    public CacheProperties() {
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getFirstCacheExpiredSeconds() {
        return this.firstCacheExpiredSeconds;
    }

    public void setFirstCacheExpiredSeconds(long firstCacheExpiredSeconds) {
        this.firstCacheExpiredSeconds = firstCacheExpiredSeconds;
    }

    public long getSecondCacheExpiredSeconds() {
        return this.secondCacheExpiredSeconds;
    }

    public void setSecondCacheExpiredSeconds(long secondCacheExpiredSeconds) {
        this.secondCacheExpiredSeconds = secondCacheExpiredSeconds;
    }

    public long getFirstCacheMaximumSize() {
        return this.firstCacheMaximumSize;
    }

    public void setFirstCacheMaximumSize(long firstCacheMaximumSize) {
        this.firstCacheMaximumSize = firstCacheMaximumSize;
    }
}

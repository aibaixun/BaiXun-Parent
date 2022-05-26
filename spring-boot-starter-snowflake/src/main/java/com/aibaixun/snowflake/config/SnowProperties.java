package com.aibaixun.snowflake.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@ConfigurationProperties(
        prefix = "bx.snow"
)
public class SnowProperties {
    private long watchDogScheduleTime = 10L;
    private long watchDogExpireTime = 30L;
    private boolean enable = true;
    private String customPrefix;

    public SnowProperties() {
    }

    public String getCustomPrefix() {
        return this.customPrefix;
    }

    public void setCustomPrefix(String customPrefix) {
        this.customPrefix = customPrefix;
    }

    public long getWatchDogScheduleTime() {
        return this.watchDogScheduleTime;
    }

    public void setWatchDogScheduleTime(long watchDogScheduleTime) {
        this.watchDogScheduleTime = watchDogScheduleTime;
    }

    public long getWatchDogExpireTime() {
        return this.watchDogExpireTime;
    }

    public void setWatchDogExpireTime(long watchDogExpireTime) {
        this.watchDogExpireTime = watchDogExpireTime;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

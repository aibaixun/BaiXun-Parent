package com.aibaixun.common.thread;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/6
 */
@Configuration
@ConfigurationProperties(prefix = "bx.thread-pool")
public class ThreadPoolAutoConfigProperties {

    private Integer corePoolSize=Runtime.getRuntime().availableProcessors();

    private Integer maximumPoolSize=Runtime.getRuntime().availableProcessors();

    private Long keepAliveTime=60L;

    private TimeUnit timeUnit=TimeUnit.SECONDS;

    private BlockQueueTypeEnum blockQueueType;

    private Integer blockQueueSize=10;

    private String poolName="bx";

    public enum BlockQueueTypeEnum{
        /**
         * 列表队列
         */
        ARRAY,
        /**
         * 单一队列
         */
        SYNCHRONOUS,
        /**
         * 链表队列
         */
        LINKED,
        /**
         * 优先级队列
         */
        PRIORITY
    }



    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public BlockQueueTypeEnum getBlockQueueType() {
        return blockQueueType;
    }

    public void setBlockQueueType(BlockQueueTypeEnum blockQueueType) {
        this.blockQueueType = blockQueueType;
    }

    public Integer getBlockQueueSize() {
        return blockQueueSize;
    }

    public void setBlockQueueSize(Integer blockQueueSize) {
        this.blockQueueSize = blockQueueSize;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}

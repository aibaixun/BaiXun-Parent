package com.aibaixun.common.listener;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public abstract class AbstractThreadPoolEvent {

    protected ThreadPoolEventTypeEnum threadPoolEventType;

    public AbstractThreadPoolEvent(ThreadPoolEventTypeEnum threadPoolEventType) {
        this.threadPoolEventType = threadPoolEventType;
    }

    public ThreadPoolEventTypeEnum getThreadPoolEventType(){
        return threadPoolEventType;
    }

    public abstract AbstractThreadPoolEvent getEvent();
}

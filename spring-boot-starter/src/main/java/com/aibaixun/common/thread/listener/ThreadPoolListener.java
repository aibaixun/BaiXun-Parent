package com.aibaixun.common.thread.listener;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public interface ThreadPoolListener {

    /**
     * 处理线程池 执行后事务
     * @param threadPoolEvent -- 线程执行事件
     */
    void handleEvent(ThreadPoolEvent threadPoolEvent);
}

package com.aibaixun.common.thread;

import java.util.concurrent.*;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public interface ITheadPoolFactory {

    /**
     * 使用默认配置构建线程池
     * @return ThreadPoolExecutor
     */
    ThreadPoolExecutor createThreadPool();

    /**
     * 创建线程池
     * @param corePoolSize 最大核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 非核心线程idle的存活时间
     * @param keepAliveTimeunit 存活时间单位
     * @param blockQueueType 阻塞队列类型
     * @param threadFactory 线程工厂
     * @param handler 饱和策略
     * @return ThreadPoolExecutor
     */
    ThreadPoolExecutor createThreadPool(Integer corePoolSize, Integer maximumPoolSize, Long keepAliveTime, TimeUnit keepAliveTimeunit, BlockQueueTypeEnum blockQueueType,Integer blockQueueSize, ThreadFactory threadFactory, RejectedExecutionHandler handler);
}

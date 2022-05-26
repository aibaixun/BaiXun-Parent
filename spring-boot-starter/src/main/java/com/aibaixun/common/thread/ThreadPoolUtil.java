package com.aibaixun.common.thread;

import com.aibaixun.basic.thread.BaseThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class ThreadPoolUtil {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);

    private static final String DEFAULT_THREAD_NAME = "bx-thread";
    private static final int DEFAULT_CORE_POOL_SIZE = 100;
    private static final int DEFAULT_MAX_POOL_SIZE = 200;
    private static final int DEFAULT_QUEUE_SIZE = 10000;
    private static final long DEFAULT_KEEPALIVETIME = 5L;


    public ThreadPoolUtil() {
    }

    public static ThreadPoolExecutor defaultThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEPALIVETIME, TimeUnit.MINUTES, new LinkedBlockingQueue(DEFAULT_QUEUE_SIZE),  BaseThreadFactory.forName("bx-thread"), new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                ThreadPoolUtil.log.warn("消息被丢弃:" + r.toString());
            }
        });
        return executor;
    }

    public static ThreadPoolExecutor nameThreadPool(String threadName) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEPALIVETIME, TimeUnit.MINUTES, new LinkedBlockingQueue(DEFAULT_QUEUE_SIZE), BaseThreadFactory.forName(threadName), new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                ThreadPoolUtil.log.warn("消息被丢弃:" + r.toString());
            }
        });
        return executor;
    }

    public static ThreadPoolExecutor poolSizeAndNameThreadPool(int corePoolSize, int maximumPoolSize, String threadName) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, DEFAULT_KEEPALIVETIME, TimeUnit.MINUTES, new LinkedBlockingQueue(DEFAULT_QUEUE_SIZE), BaseThreadFactory.forName(threadName), new RejectedExecutionHandler() {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                ThreadPoolUtil.log.warn("消息被丢弃:" + r.toString());
            }
        });
        return executor;
    }

    public static ThreadPoolExecutor fullDefinedThreadPool(int corePoolSize, int maximumPoolSize, String threadName, int queueSize, RejectedExecutionHandler handler) {
        assert handler != null;

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, DEFAULT_KEEPALIVETIME, TimeUnit.MINUTES, new LinkedBlockingQueue(queueSize), BaseThreadFactory.forName(threadName), handler);
    }

}

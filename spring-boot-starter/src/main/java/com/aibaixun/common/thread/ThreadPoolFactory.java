package com.aibaixun.common.thread;

import com.aibaixun.basic.thread.BaseThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class ThreadPoolFactory implements ITheadPoolFactory {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static final Integer DEFAULT_CORE_THREAD_SIZE = Runtime.getRuntime().availableProcessors();
    private static final Integer DEFAULT_MAXIMUM_THREAD_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final Long DEFAULT_KEEP_ALIVE = 60L;
    private static final TimeUnit DEFAULT_KEEP_ALIVE_TIMEUNIT = TimeUnit.SECONDS;
    private static final Integer DEFAULT_BLOCKINGQUEUE_CAPACITY = 10;
    private static final String DEFAULT_THREAD_PREFIX_NAME = "bx";

    private ThreadPoolFactory() {
    }

    public static ThreadPoolFactory instance() {
        return new ThreadPoolFactory();
    }


    @Override
    public ThreadPoolExecutor createThreadPool() {
        return createThreadPool(DEFAULT_CORE_THREAD_SIZE, DEFAULT_MAXIMUM_THREAD_SIZE, DEFAULT_KEEP_ALIVE, DEFAULT_KEEP_ALIVE_TIMEUNIT, BlockQueueTypeEnum.ARRAY, DEFAULT_BLOCKINGQUEUE_CAPACITY, BaseThreadFactory.forName(DEFAULT_THREAD_PREFIX_NAME), new CustomRejectedExecutionHandler());
    }

    @Override
    public ThreadPoolExecutor createThreadPool(Integer corePoolSize, Integer maximumPoolSize, Long keepAliveTime, TimeUnit keepAliveTimeunit, BlockQueueTypeEnum blockQueueType, Integer blockQueueSize, ThreadFactory threadFactory, RejectedExecutionHandler rejectedHandler) {
        corePoolSize = Optional.ofNullable(corePoolSize).orElse(DEFAULT_CORE_THREAD_SIZE);
        maximumPoolSize = Optional.ofNullable(maximumPoolSize).orElse(DEFAULT_MAXIMUM_THREAD_SIZE);
        keepAliveTime = Optional.ofNullable(keepAliveTime).orElse(DEFAULT_KEEP_ALIVE);
        keepAliveTimeunit = Optional.ofNullable(keepAliveTimeunit).orElse(DEFAULT_KEEP_ALIVE_TIMEUNIT);
        blockQueueSize = Optional.ofNullable(blockQueueSize).orElse(DEFAULT_BLOCKINGQUEUE_CAPACITY);
        threadFactory = Optional.ofNullable(threadFactory).orElseGet(() -> BaseThreadFactory.forName(DEFAULT_THREAD_PREFIX_NAME));
        rejectedHandler = Optional.ofNullable(rejectedHandler).orElseGet(CustomRejectedExecutionHandler::new);
        blockQueueType = Optional.ofNullable(blockQueueType).orElse(BlockQueueTypeEnum.ARRAY);
        BlockingQueue<Runnable> blockingQueue = getBlockingQueue(blockQueueType, blockQueueSize);
        logger.info("线程池参数—--核心线程数:{}，最大线程数:{}，系统cpu数:{}，阻塞队列类型：{}", corePoolSize, maximumPoolSize, DEFAULT_CORE_THREAD_SIZE, blockQueueType);
        return new BaseThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, keepAliveTimeunit, blockingQueue, threadFactory, rejectedHandler);
    }

    private BlockingQueue<Runnable> getBlockingQueue(BlockQueueTypeEnum blockQueueType, Integer blockQueueSize) {
        BlockingQueue<Runnable> queue;
        switch (blockQueueType) {
            case ARRAY:
                queue = new ArrayBlockingQueue<>(blockQueueSize);
                break;
            case SYNCHRONOUS:
                queue = new SynchronousQueue<>();
                break;
            case LINKED:
                queue = new LinkedBlockingQueue<>();
                break;
            case PRIORITY:
                queue = new PriorityBlockingQueue<>(blockQueueSize);
                break;
            default:
                throw new RuntimeException();
        }
        return queue;
    }


}

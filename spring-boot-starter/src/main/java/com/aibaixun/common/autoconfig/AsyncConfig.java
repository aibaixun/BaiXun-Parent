package com.aibaixun.common.autoconfig;

import com.aibaixun.basic.thread.BaseThreadFactory;
import com.aibaixun.common.thread.AsyncExceptionHandler;
import com.aibaixun.common.thread.BaseThreadPoolExecutor;
import com.aibaixun.common.thread.ThreadPoolAutoConfigProperties;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/01/06
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolAutoConfigProperties.class)
public class AsyncConfig implements AsyncConfigurer {

    private ThreadPoolAutoConfigProperties threadPoolAutoConfigProperties;

    public void setThreadPoolAutoConfigProperties(ThreadPoolAutoConfigProperties threadPoolAutoConfigProperties) {
        this.threadPoolAutoConfigProperties = threadPoolAutoConfigProperties;
    }

    @Bean(name = "baseThreadPool")
    @Override
    public Executor getAsyncExecutor() {
        Executor executor = new BaseThreadPoolExecutor(
                threadPoolAutoConfigProperties.getCorePoolSize(),
                threadPoolAutoConfigProperties.getMaximumPoolSize(),
                threadPoolAutoConfigProperties.getKeepAliveTime(),
                threadPoolAutoConfigProperties.getTimeUnit(),
                getBlockingQueue(threadPoolAutoConfigProperties.getBlockQueueType(), threadPoolAutoConfigProperties.getBlockQueueSize()),
                BaseThreadFactory.forName(threadPoolAutoConfigProperties.getPoolName()),
                new ThreadPoolExecutor.AbortPolicy()
        );
        return executor;
    }

    private BlockingQueue<Runnable> getBlockingQueue(ThreadPoolAutoConfigProperties.BlockQueueTypeEnum blockQueueType, Integer blockQueueSize) {

        BlockingQueue<Runnable> queue;
        ThreadPoolAutoConfigProperties.BlockQueueTypeEnum typeEnum = Optional.ofNullable(blockQueueType).orElse(ThreadPoolAutoConfigProperties.BlockQueueTypeEnum.ARRAY);
        switch (typeEnum){
            case ARRAY:
                queue=new ArrayBlockingQueue<>(blockQueueSize);
                break;
            case SYNCHRONOUS:
                queue=new SynchronousQueue<>();
                break;
            case LINKED:
                queue=new LinkedBlockingQueue<>();
                break;
            case PRIORITY:
                queue=new PriorityBlockingQueue<>(blockQueueSize);
                break;
            default:
                throw new RuntimeException();
        }
        return queue;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}

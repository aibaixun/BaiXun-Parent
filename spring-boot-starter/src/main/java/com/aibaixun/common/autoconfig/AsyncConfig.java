package com.aibaixun.common.autoconfig;

import com.aibaixun.basic.thread.BaseThreadFactory;
import com.aibaixun.common.thread.BaseThreadPoolExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/01/06
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "bxThreadPool")
    @Override
    public Executor getAsyncExecutor() {
        Executor executor = new BaseThreadPoolExecutor(
                //设置核心线程数
                10,

                //设置最大线程数
                100,

                //设置非核心线程idle时的存活时间
                60,

                //存活时间单位
                TimeUnit.SECONDS,

                //设置阻塞队列
                new ArrayBlockingQueue<>(10),

                //设置线程工厂
                BaseThreadFactory.forName("bx"),

                //设置饱和策略(调用线程运行)
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}

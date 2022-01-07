package com.aibaixun.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        log.error("Async method has uncaught exception, params:{}" + Arrays.toString(params));

        if (throwable instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) throwable;
            log.error("asyncException:"  + asyncException.getMessage());
        }

        log.error("Exception :", throwable);
    }
}

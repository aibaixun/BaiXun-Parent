package com.aibaixun.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/01/06
 */
public class BaseThreadPoolExecutor extends ThreadPoolExecutor {
    protected static final Logger logger = LoggerFactory.getLogger(BaseThreadPoolExecutor.class);



    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        super.afterExecute(runnable, throwable);
        logExceptionsAfterExecute(runnable, throwable);
    }

    private void logExceptionsAfterExecute(Runnable runnable, Throwable throwable) {
        Throwable hiddenThrowable = extractThrowable(runnable);
        if (hiddenThrowable != null) {
            handleOrLog(hiddenThrowable);
        }
        if (throwable != null && Thread.getDefaultUncaughtExceptionHandler() == null) {
            handleOrLog(throwable);
        }
    }

    private void handleOrLog(Throwable throwable) {
        if (Thread.getDefaultUncaughtExceptionHandler() == null) {
            logger.error("Error in ThreadPoolExecutor", throwable);
        }else {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), throwable);
        }
    }

    private Throwable extractThrowable(Runnable runnable) {
        if ((runnable instanceof Future<?>) && ((Future<?>) runnable).isDone())
        {
            try {
                ((Future<?>) runnable).get();
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            } catch (CancellationException e) {
                logger.trace("Task cancelled", e);
            } catch (ExecutionException e) {
                return e.getCause();
            }
        }
        return null;

    }


}

package com.aibaixun.common.listener;

import com.aibaixun.common.thread.ThreadException;
import com.aibaixun.common.thread.BaseThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class ThreadPoolListener implements IThreadPoolListener{

    private static final Logger logger = LoggerFactory.getLogger(BaseThreadPoolExecutor.class);

    @Override
    public void handleEvent(ThreadPoolEvent threadPoolEvent) {
        Throwable hiddenThrowable = extractThrowable(threadPoolEvent.getRunnable());
        if (hiddenThrowable != null) {
            handleOrLog(hiddenThrowable);
        }
        if (threadPoolEvent.getThrowable() != null && Thread.getDefaultUncaughtExceptionHandler() == null) {
            handleOrLog(threadPoolEvent.getThrowable());
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

    private void processingNormalEvent() {

    }
}

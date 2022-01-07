package com.aibaixun.common.listener;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class ThreadPoolErrorEvent extends AbstractThreadPoolEvent {
    private Runnable runnable;
    private Throwable throwable;

    private ThreadPoolErrorEvent(Runnable runnable, Throwable throwable) {
        super(ThreadPoolEventTypeEnum.ERROR);
        this.runnable = runnable;
        this.throwable = throwable;
    }

    public static ThreadPoolErrorEvent instant(Runnable runnable, Throwable throwable){
        return new ThreadPoolErrorEvent(runnable,throwable);
    }

    @Override
    public AbstractThreadPoolEvent getEvent() {
        return this;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}

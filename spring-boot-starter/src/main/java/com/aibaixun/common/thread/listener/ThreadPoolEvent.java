package com.aibaixun.common.thread.listener;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class ThreadPoolEvent{
    private final Runnable runnable;
    private Throwable throwable;
    private Thread thread;

    private ThreadPoolEvent(Runnable runnable, Throwable throwable) {
        this.runnable = runnable;
        this.throwable = throwable;
    }

    private ThreadPoolEvent(Runnable runnable, Thread thread) {
        this.runnable = runnable;
        this.thread = thread;
    }

    public static ThreadPoolEvent instant(Runnable runnable, Throwable throwable){
        return new ThreadPoolEvent(runnable,throwable);
    }

    public static ThreadPoolEvent instant(Runnable runnable, Thread thread){
        return new ThreadPoolEvent(runnable,thread);
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Thread getThread() {
        return thread;
    }
}

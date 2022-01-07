package com.aibaixun.common.thread;

import com.aibaixun.common.listener.IThreadPoolListener;
import com.aibaixun.common.listener.ThreadPoolErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/01/06
 */
public class BaseThreadPoolExecutor extends ThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BaseThreadPoolExecutor.class);

    private List<IThreadPoolListener> listeners;

    public BaseThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        listeners=new ArrayList<>();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        super.afterExecute(runnable, throwable);
        ThreadPoolErrorEvent event = ThreadPoolErrorEvent.instant(runnable, throwable);
        listeners.forEach(e->e.handleEvent(event));
    }

    public Boolean registerListener(IThreadPoolListener listener){
        int size = listeners.size()+1;
        listeners.add(listener);
        return size==listeners.size();
    }

    public Boolean removeListener(IThreadPoolListener listener){
        int size = listeners.size()-1;
        int i = listeners.indexOf(listener);
        if(i>0){
            listeners.remove(listener);
        }
        return size<0?Boolean.TRUE:size==listeners.size();
    }
}

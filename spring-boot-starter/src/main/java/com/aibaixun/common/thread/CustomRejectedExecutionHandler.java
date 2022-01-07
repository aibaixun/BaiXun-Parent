package com.aibaixun.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    public final Logger logger = LoggerFactory.getLogger(CustomRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.error("Task " + r.toString() + " rejected from " + executor.toString());
    }
}

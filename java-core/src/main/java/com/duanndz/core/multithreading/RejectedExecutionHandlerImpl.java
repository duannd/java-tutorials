package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * duan.nguyen
 * Datetime 4/2/20 01:21
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    private static final Logger log = LoggerFactory.getLogger(RejectedExecutionHandlerImpl.class);

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        log.info(runnable.toString() + " is rejected");
    }
}

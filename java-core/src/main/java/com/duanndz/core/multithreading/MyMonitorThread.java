package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * duan.nguyen
 * Datetime 4/2/20 01:25
 */
public class MyMonitorThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(MyMonitorThread.class);

    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run = true;

    public MyMonitorThread(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
    }

    public void shutdown() {
        this.run = false;
    }

    @Override
    public void run() {
        while (run) {
            log.info("[monitor] [{}/{}] Active: {}, Completed: {}, Task: {}, isShutdown: {}, isTerminated: {}",
                    executor.getPoolSize(), executor.getCorePoolSize(),
                    executor.getActiveCount(), executor.getCompletedTaskCount(), executor.getTaskCount(),
                    executor.isShutdown(), executor.isTerminated());
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

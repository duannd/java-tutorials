package com.duanndz.concurrency.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * duan.nguyen
 * Datetime 5/20/20 10:29
 */
@Slf4j
public class CallApiTask implements Callable<Long> {

    private final long processingTime;
    private final String taskName;

    public CallApiTask() {
        this(1000);
    }

    public CallApiTask(long processingTime) {
        this(processingTime, "CallAPI");
    }

    public CallApiTask(long processingTime, String taskName) {
        this.processingTime = processingTime;
        this.taskName = taskName;
    }

    @Override
    public Long call() throws Exception {
        run();
        return 1L;
    }

    public void run() {
        try {
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Task: {} processing in {} milliseconds", taskName, processingTime);
    }
}

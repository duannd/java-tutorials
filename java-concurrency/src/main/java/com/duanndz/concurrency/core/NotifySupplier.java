package com.duanndz.concurrency.core;

import java.util.function.Supplier;

/**
 * duan.nguyen
 * Datetime 5/20/20 11:06
 */
public class NotifySupplier implements Supplier<String> {

    private final long processingTime;
    private final String taskName;

    public NotifySupplier(long processingTime, String taskName) {
        this.taskName = taskName;
        this.processingTime = processingTime;
    }

    @Override
    public String get() {
        try {
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("Task %s proccessing in %d milliseconds", taskName, processingTime);
    }
}

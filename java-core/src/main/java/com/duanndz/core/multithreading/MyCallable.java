package com.duanndz.core.multithreading;

import java.util.concurrent.Callable;

/**
 * duan.nguyen
 * Datetime 4/2/20 08:21
 */
public class MyCallable implements Callable<String> {

    private long waitTime;

    public MyCallable() {
        this.waitTime = 1000;
    }

    public MyCallable(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(this.waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
}

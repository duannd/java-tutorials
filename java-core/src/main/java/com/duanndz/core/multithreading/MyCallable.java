package com.duanndz.core.multithreading;

import java.util.concurrent.Callable;

/**
 * duan.nguyen
 * Datetime 4/2/20 08:21
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
}

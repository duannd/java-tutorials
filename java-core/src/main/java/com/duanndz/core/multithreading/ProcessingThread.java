package com.duanndz.core.multithreading;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * duan.nguyen
 * Datetime 3/30/20 15:14
 */
@Getter
public class ProcessingThread implements Runnable {

    private AtomicInteger count = new AtomicInteger(0);
    private final Object mutex = new Object();

    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            processSomething(i);
            count.getAndAdd(1);
//            synchronized (mutex) {
//                count++;
//            }
        }
    }

    private void processSomething(int i) {
        // processing some job
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

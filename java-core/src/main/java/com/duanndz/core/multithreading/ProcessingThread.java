package com.duanndz.core.multithreading;

import lombok.Getter;

/**
 * duan.nguyen
 * Datetime 3/30/20 15:14
 */
@Getter
public class ProcessingThread implements Runnable {

    private int count;
    private final Object mutex = new Object();

    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            processSomething(i);
            synchronized (mutex) {
                count++;
            }
        }
    }

    private void processSomething(int i) {
        // processing some job
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

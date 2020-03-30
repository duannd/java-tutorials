package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 10:30
 */
public class MyThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(MyThread.class);

    private int sleep;

    public MyThread(String name, int sleep) {
        super(name);
        this.sleep = sleep;
    }

    @Override
    public void run() {
        log.info("MyThread - START {}", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            //Get database connection, delete unused data from DB
            doDBProcessing();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("MyThread - END {}", Thread.currentThread().getName());
    }

    private void doDBProcessing() throws InterruptedException {
        Thread.sleep(sleep * 1000);
    }
}

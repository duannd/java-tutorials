package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 10:27
 */
public class HeavyWorkRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HeavyWorkRunnable.class);

    private int sleep;

    public HeavyWorkRunnable(int sleep) {
        this.sleep = sleep;
    }

    @Override
    public void run() {
        log.info("Doing heavy processing - START {}", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            //Get database connection, delete unused data from DB
            doDBProcessing();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("Doing heavy processing - END {}", Thread.currentThread().getName());
    }

    private void doDBProcessing() throws InterruptedException {
        Thread.sleep(sleep * 1000);
    }

}

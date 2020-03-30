package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 10:34
 */
public class MultiThreadExample {

    private static final Logger log = LoggerFactory.getLogger(MultiThreadExample.class);

    public static void main(String[] args) {
        Thread t1 = new Thread(new HeavyWorkRunnable(1), "t1");
        Thread t2 = new Thread(new HeavyWorkRunnable(2), "t2");
        log.info("Starting Runnable threads");
        t1.start();
        t2.start();
        log.info("Runnable Threads has been started");
        Thread t3 = new MyThread("t3", 3);
        Thread t4 = new MyThread("t4", 4);
        log.info("Starting MyThreads");
        t3.start();
        t4.start();
        log.info("MyThreads has been started");
    }

}

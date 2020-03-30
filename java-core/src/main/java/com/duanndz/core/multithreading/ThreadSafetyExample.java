package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 15:15
 */
public class ThreadSafetyExample {

    private static final Logger log = LoggerFactory.getLogger(ThreadSafetyExample.class);

    public static void main(String[] args) throws InterruptedException {
        ProcessingThread pt = new ProcessingThread();
        Thread t1 = new Thread(pt, "t1");
        Thread t2 = new Thread(pt, "t2");
        Thread t3 = new Thread(pt, "t3");
        t1.start();
        t2.start();
        t3.start();
        //wait for threads to finish processing
        t1.join();
        t2.join();
        t3.join();
        log.info("Processing count= {}", pt.getCount().get());
    }

}

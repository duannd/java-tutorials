package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 11:21
 */
public class ThreadJoinExample {

    private static final Logger log = LoggerFactory.getLogger(ThreadJoinExample.class);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new MyThread("t1", 6);
        Thread t2 = new MyThread("t2", 8);
        Thread t3 = new MyThread("t3", 10);
        log.info("Start t1 and call join(2000)");
        // second: 0
        t1.start(); // t1 will sleep 7 seconds
        t1.join(2000);
        // second: 2
        t2.start(); // start second thread after waiting for 2 seconds or t1 dead (start after 2000 s).
        t1.join();
        // second: 7
        t3.start(); //start third thread only when t1 is dead

        //let all threads finish execution before finishing main thread
        t1.join(); // t1 done
        t2.join(); // t2 done at second 11.
        t3.join(); // t3 done at second 18
        log.info("All threads are dead, exiting main thread (total is 18 seconds)");
    }

}

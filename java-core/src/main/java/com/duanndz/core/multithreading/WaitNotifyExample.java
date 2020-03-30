package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 14:03
 */
public class WaitNotifyExample {

    private static final Logger log = LoggerFactory.getLogger(WaitNotifyExample.class);

    public static void main(String[] args) {
        Message message = new Message("process it");
        Waiter waiter = new Waiter(message);
        new Thread(waiter, "w1").start();
        // initial waiter 2:
        Waiter waiter2 = new Waiter(message);
        new Thread(waiter2, "w2").start();

        // initial notify
        Notifier notifier = new Notifier(message);
        new Thread(notifier, "notifier").start();
        log.info("All the threads are started");
    }
}

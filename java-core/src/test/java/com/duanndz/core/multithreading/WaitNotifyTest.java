package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * duan.nguyen
 * Datetime 3/30/20 13:58
 */
@RunWith(JUnit4.class)
public class WaitNotifyTest {

    @Test
    public void waitNotify_message() {
        Message message = new Message("process it");
        Waiter waiter = new Waiter(message);
        new Thread(waiter, "w1").start();
        // initial waiter 2:
        Waiter waiter2 = new Waiter(message);
        new Thread(waiter2, "w2").start();

        // initial notify
        Notifier notifier = new Notifier(message);
        new Thread(notifier, "notifier").start();
        System.out.println("All threads are started");
    }

}

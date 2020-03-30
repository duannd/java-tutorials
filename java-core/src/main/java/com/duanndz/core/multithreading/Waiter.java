package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 13:40
 */
@AllArgsConstructor
public class Waiter implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Waiter.class);

    private final Message message;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (message) {
            log.info("{} waiting to get notified at time: {}", name, System.currentTimeMillis());
            try {
                message.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " " + System.currentTimeMillis());
            log.info("{} waiter thread got notified at time: {}", name, System.currentTimeMillis());
            //process the message now
            log.info("{} processed: {}", name, message.getMsg());
        }
    }
}

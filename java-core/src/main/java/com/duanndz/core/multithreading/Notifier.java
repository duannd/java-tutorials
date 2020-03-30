package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 3/30/20 13:46
 */
@AllArgsConstructor
public class Notifier implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Notifier.class);

    private final Message message;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        log.info("{} started", name);
        try {
            Thread.sleep(5000);
            synchronized (message) {
                message.setMsg(name + " Notifier work done");
                // message.notify();
                message.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

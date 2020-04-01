package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * duan.nguyen
 * Datetime 4/2/20 00:34
 */
public class Consumer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Message msg;
            //consuming messages until exit message is received
            Thread.sleep(10 * 1000);
            while ((msg = queue.take()).getMsg() != null && !msg.getMsg().equals("exit")) {
                Thread.sleep(10);
                log.info("Consumed {}", msg.getMsg());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

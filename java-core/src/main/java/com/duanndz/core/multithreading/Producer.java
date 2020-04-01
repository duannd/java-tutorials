package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * duan.nguyen
 * Datetime 4/2/20 00:29
 */
public class Producer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    private BlockingQueue<Message> queue;

    public Producer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        //produce messages
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("msg " + i);
            try {
                Thread.sleep(i);
                queue.put(msg);
                log.info("Produced {}", msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //adding exit message
        Message msg = new Message("exit");
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

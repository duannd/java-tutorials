package com.duanndz.core.multithreading;

import com.duanndz.core.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * duan.nguyen
 * Datetime 4/2/20 00:39
 */
public class ProducerConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ProducerConsumerService.class);

    public static void main(String[] args) {
        //Creating BlockingQueue of size 10
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        //starting producer to produce messages in queue
        new Thread(producer).start();
        //starting consumer to consume messages from queue
        new Thread(consumer).start();
        log.info("Producer and Consumer has been started");
    }

}

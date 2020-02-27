package com.duanndz.rabbitmq.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created By ngdduan@gmail.com at 2/26/20 5:01 PM
 */
@Component
@Slf4j
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("Received <{}> from queue 1", message);
        latch.countDown();
    }

    public void receiveMessage2(String message) {
        log.info("Received <{}> from queue 2", message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}

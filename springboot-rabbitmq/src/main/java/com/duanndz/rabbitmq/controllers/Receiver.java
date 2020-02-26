package com.duanndz.rabbitmq.controllers;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created By ngdduan@gmail.com at 2/26/20 5:01 PM
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}

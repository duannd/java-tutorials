package com.duanndz.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        log.info("Receiving a message start...");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("password");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        log.info("[*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received '{}'", message);
        };
        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
        });
    }

}

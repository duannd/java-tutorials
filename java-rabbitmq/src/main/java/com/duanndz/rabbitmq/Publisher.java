package com.duanndz.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

public class Publisher {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        log.info("Sending a message start...");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("password");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // Queue Declare.
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Sending message
            String message = "Hello World! " + DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss.SSS")
                    .format(LocalDateTime.now());
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            log.info(" [x] Sent '{}'", message);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

}

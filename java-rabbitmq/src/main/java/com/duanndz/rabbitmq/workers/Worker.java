package com.duanndz.rabbitmq.workers;

import com.duanndz.rabbitmq.RabbitUtils;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Worker {

    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    public static void main(String[] args) throws Exception {
        log.info("[*] Waiting for messages. To exit press CTRL+C");
        ConnectionFactory factory = RabbitUtils.getConnectionFactory();
        var connection = factory.newConnection();
        var channel = connection.createChannel();
        channel.queueDeclare(Tasks.TASK_QUEUE_NAME, false, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received '{}'", message);
            try {
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info(" [x] Processing Done of {}", message);
                log.info("-------------------------------------------------------------------------");
            }
        };

        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(Tasks.TASK_QUEUE_NAME, autoAck, callback, consumerTag -> {
        });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                Thread.sleep(1000);
        }
    }

}

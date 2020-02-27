package com.duanndz.rabbitmq.workers;

import com.duanndz.rabbitmq.RabbitUtils;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Tasks {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        System.out.println("Running task starting. Please enter 'exit' to exit program.");
        Scanner scanner = new Scanner(System.in);
        ConnectionFactory factory = RabbitUtils.getConnectionFactory();
        var connection = factory.newConnection();
        var channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        while (true) {
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                System.out.println("Thank you! See you again.");
                return;
            }
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}

package com.duanndz.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitUtils {

    private RabbitUtils() {

    }

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("password");
        return factory;
    }

}

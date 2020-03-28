package com.duanndz.rabbitmq.config;

import com.duanndz.rabbitmq.controllers.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created By ngdduan@gmail.com at 2/26/20 4:49 PM
 */
@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange";
    private static final String QUEUE_NAME = "spring-boot";
    private static final String QUEUE_NAME_2 = "spring-boot-2";

    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        log.info("Initial Message Listener adapter");
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
        messageListener.addQueueOrTagToMethodName(QUEUE_NAME, "receiveMessage");
        messageListener.addQueueOrTagToMethodName(QUEUE_NAME_2, "receiveMessage2");
        return messageListener;
    }

    // Step 1: Configure a message listener container.
    // Created new connection: rabbitConnectionFactory#7b4a0aef:0/SimpleConnection@7c369270 [delegate=amqp://admin@127.0.0.1:5672/, localPort= 56195]
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        log.info("Configure a message listener container");
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueueNames(QUEUE_NAME, QUEUE_NAME_2);
        // container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    // Step 2: Declare the queue, the exchange, and the binding between them.
    @Bean
    public TopicExchange exchange() {
        log.info("Declare topic exchange");
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    // Auto-declaring a non-durable, auto-delete, or exclusive Queue (spring-boot) durable:false, auto-delete:false, exclusive:false.
    // It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.
    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME_2, false);
    }

    @Bean
    public Binding binding(TopicExchange exchange, Queue queue1) {
        log.info("Declare Queue 1 and bind it to exchange");
        return BindingBuilder.bind(queue1).to(exchange).with("foo.bar.#");
    }

    @Bean
    public Binding binding2(TopicExchange exchange, Queue queue2) {
        log.info("Declare Queue 2 and bind it to exchange");
        return BindingBuilder.bind(queue2).to(exchange).with("springboot.rabbit.#");
    }

}

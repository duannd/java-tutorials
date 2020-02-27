package com.duanndz.rabbitmq.config;

import com.duanndz.rabbitmq.controllers.BookReceiver;
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

@Slf4j
@Configuration
public class BookRabbitConfiguration {

    public static final String BOOK_TOPIC_EXCHANGE = "book-topic-exchange";
    public static final String ADD_BOOK_QUEUE = "add-book-queue";

    // Configure a message listener container.
    @Bean
    public MessageListenerAdapter bookListenerAdapter(BookReceiver bookReceiver) {
        return new MessageListenerAdapter(bookReceiver, "addBook");
    }

    @Bean
    public SimpleMessageListenerContainer bookListenerContainer(ConnectionFactory connectionFactory,
                                                                MessageListenerAdapter bookListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ADD_BOOK_QUEUE);
        container.setMessageListener(bookListenerAdapter);
        return container;
    }

    // Step 2: Declare the queue, the exchange, and the binding between them.
    @Bean
    public TopicExchange bookTopicExchange() {
        return new TopicExchange(BOOK_TOPIC_EXCHANGE);
    }

    @Bean
    public Queue addBookQueue() {
        return new Queue(ADD_BOOK_QUEUE, false);
    }

    @Bean
    public Binding bookBinding(TopicExchange bookTopicExchange, Queue addBookQueue) {
        return BindingBuilder.bind(addBookQueue).to(bookTopicExchange).with("book.add.#");
    }

}

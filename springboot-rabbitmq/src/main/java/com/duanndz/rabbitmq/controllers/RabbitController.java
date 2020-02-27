package com.duanndz.rabbitmq.controllers;

import com.duanndz.rabbitmq.config.RabbitConfiguration;
import com.duanndz.rabbitmq.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * Created By ngdduan@gmail.com at 2/26/20 4:51 PM
 */
@RestController
@RequestMapping("/api/rabbit")
@Slf4j
public class RabbitController {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public RabbitController(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody @Valid Message message) throws InterruptedException {
        log.info("Send message {}", message);
        rabbitTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE_NAME, "foo.bar.baz", message.getContent());
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().build();
    }

    @PostMapping("queue-2")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE_NAME, "springboot.rabbit.queue2", message);
        return ResponseEntity.ok(String.format("%s %s", "queue2", message));
    }

}

package com.duanndz.rabbitmq.controllers;

import com.duanndz.rabbitmq.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By ngdduan@gmail.com at 2/26/20 4:51 PM
 */
@RestController
@RequestMapping("/api/rabbit")
@Slf4j
public class RabbitController {

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody Message message) {
        log.info("Send message {}", message);
        return ResponseEntity.ok().build();
    }

}

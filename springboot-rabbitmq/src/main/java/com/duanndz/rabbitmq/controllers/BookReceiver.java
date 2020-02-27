package com.duanndz.rabbitmq.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookReceiver {

    public void addBook(String name) {
        log.info("Add Book {}", name);
    }

}

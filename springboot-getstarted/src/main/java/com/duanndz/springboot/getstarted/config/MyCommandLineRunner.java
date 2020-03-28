package com.duanndz.springboot.getstarted.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.debug("This method is called just before SpringApplication.run(…) completes.");
    }

}

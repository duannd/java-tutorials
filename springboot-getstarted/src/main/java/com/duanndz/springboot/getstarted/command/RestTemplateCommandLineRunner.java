package com.duanndz.springboot.getstarted.command;

import com.duanndz.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * duan.nguyen
 * Datetime 5/11/20 15:42
 */
@Slf4j
@Component
public class RestTemplateCommandLineRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;

    public RestTemplateCommandLineRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {
        String url = "http://localhost:8080/api/users";
        User user = new User();
        user.setFirstName("Duan");
        user.setLastName("Nguyen");
        User response = this.restTemplate.postForObject(url, user, User.class);
        log.info("Response: {}", response);
    }
}

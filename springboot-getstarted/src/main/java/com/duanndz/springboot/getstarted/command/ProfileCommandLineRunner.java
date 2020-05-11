package com.duanndz.springboot.getstarted.command;

import com.duanndz.springboot.getstarted.profiles.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProfileCommandLineRunner implements CommandLineRunner {

    private final ProfileService profileService;

    public ProfileCommandLineRunner(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void run(String... args) {
        log.info("This method is called just before SpringApplication.run(…) completes.");
        log.info("Env {}", profileService.getProfile());
    }

}

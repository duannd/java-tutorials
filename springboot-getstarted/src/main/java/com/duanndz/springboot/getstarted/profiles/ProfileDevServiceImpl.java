package com.duanndz.springboot.getstarted.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * duan.nguyen
 * Datetime 5/4/20 16:43
 */
@Component
@Profile("development")
public class ProfileDevServiceImpl extends AbstractProfileService {

    @Override
    public String getProfile() {
        return "Development";
    }
}

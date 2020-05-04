package com.duanndz.springboot.getstarted.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * duan.nguyen
 * Datetime 5/4/20 16:45
 */
@Component
@Profile({"default", "prod"})
public class ProfileServiceImpl extends AbstractProfileService {

    @Override
    public String getProfile() {
        return "Production or Default";
    }

}

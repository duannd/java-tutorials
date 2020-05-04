package com.duanndz.springboot.getstarted.profiles;

/**
 * duan.nguyen
 * Datetime 5/4/20 16:41
 */
public abstract class AbstractProfileService implements ProfileService {

    @Override
    public String getProfile() {
        throw new UnsupportedOperationException();
    }
}

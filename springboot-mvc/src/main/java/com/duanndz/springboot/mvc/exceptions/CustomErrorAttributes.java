package com.duanndz.springboot.mvc.exceptions;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

/**
 * duan.nguyen
 * Datetime 5/11/20 13:25
 */
// @Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        // Let Spring handle the error first, we will modify later :)
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("timestamp", Instant.now());
        // insert a new key
        errorAttributes.put("version", "1.2");
        return errorAttributes;
    }
}

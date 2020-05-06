package com.duanndz.springboot.mvc.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * duan.nguyen
 * Datetime 5/6/20 10:21
 */
@Getter
@Setter
@ToString(of = {"error", "message", "path", "status", "timestamp"})
public class ErrorResponseEntity {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponseEntity() {
        this.timestamp = Instant.now();
    }

    public ErrorResponseEntity(MvcErrorCode errorCode) {
        this.timestamp = Instant.now();
        this.error = errorCode.name();
        this.message = errorCode.getErrorMessage();
    }

}

package com.duanndz.springboot.mvc.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> errors = new ArrayList<>();

    public ErrorResponseEntity() {
        this.timestamp = Instant.now();
    }

    public ErrorResponseEntity(MvcErrorCode errorCode) {
        this.timestamp = Instant.now();
        this.error = errorCode.name();
        this.message = errorCode.getErrorMessage();
    }

    public void addError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        this.errors.add(error);
    }

    public void addAllErrors(List<String> errors) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        if (errors != null && !errors.isEmpty()) {
            this.errors.addAll(errors);
        }
    }

}

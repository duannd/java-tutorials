package com.duanndz.springboot.mvc.exceptions;

/**
 * duan.nguyen
 * Datetime 5/6/20 10:15
 */
public enum MvcErrorCode {
    ERR_USER_NOT_FOUND("User not found!!!")
    ;

    private final String errorMessage;

    MvcErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

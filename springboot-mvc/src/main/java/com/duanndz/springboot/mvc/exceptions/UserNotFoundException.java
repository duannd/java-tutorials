package com.duanndz.springboot.mvc.exceptions;

/**
 * duan.nguyen
 * Datetime 5/6/20 10:09
 */
public class UserNotFoundException extends RuntimeException {

    private final MvcErrorCode errorCode;

    public UserNotFoundException(MvcErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public UserNotFoundException(MvcErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public UserNotFoundException(MvcErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserNotFoundException(MvcErrorCode errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public MvcErrorCode getErrorCode() {
        return errorCode;
    }

}

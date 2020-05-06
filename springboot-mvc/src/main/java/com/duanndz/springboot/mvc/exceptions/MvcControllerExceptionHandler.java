package com.duanndz.springboot.mvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * duan.nguyen
 * Datetime 5/5/20 16:37
 */
@ControllerAdvice
public class MvcControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ErrorResponseEntity handleUserException(HttpServletRequest request, UserNotFoundException ex) {
        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(ex.getErrorCode());
        errorResponseEntity.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseEntity.setPath(request.getRequestURI());
        return errorResponseEntity;
    }

}

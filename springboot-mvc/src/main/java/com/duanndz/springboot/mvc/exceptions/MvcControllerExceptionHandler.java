package com.duanndz.springboot.mvc.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * duan.nguyen
 * Datetime 5/5/20 16:37
 *
 * @see "https://mkyong.com/spring-boot/spring-rest-error-handling-example/"
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

    // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity(MvcErrorCode.ERR_BAD_REQUEST);
        errorResponseEntity.setStatus(HttpStatus.BAD_REQUEST.value());
        if (request instanceof ServletWebRequest) {
            errorResponseEntity.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        }
        //Get all fields errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s :: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        errorResponseEntity.addAllErrors(errors);
        return new ResponseEntity<>(errorResponseEntity, headers, status);

    }

}

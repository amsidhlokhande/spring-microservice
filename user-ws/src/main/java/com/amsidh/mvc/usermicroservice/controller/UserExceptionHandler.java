package com.amsidh.mvc.usermicroservice.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amsidh.mvc.usermicroservice.exception.UserException;
import com.amsidh.mvc.usermicroservice.ui.response.ErrorMessage;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) throws Exception {
        return ResponseEntity.internalServerError().body(ErrorMessage.builder().timestamp(new Date()).errorMessage(ex.getLocalizedMessage()).build());
    }

    @ExceptionHandler({UserException.class, NullPointerException.class})
    public final ResponseEntity<Object> handleUserException(UserException ex, WebRequest request) throws Exception {
        return ResponseEntity.internalServerError().body(ErrorMessage.builder().timestamp(new Date()).errorMessage(ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString()).build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.internalServerError().body(ErrorMessage.builder().timestamp(new Date()).errorMessage(ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString()).build());
        //return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}

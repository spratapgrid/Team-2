package com.forge.talentAcquisitionEngine.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BusinessException extends RuntimeException {
    private HttpStatus status;
    private List<String> message;

    public BusinessException(HttpStatus status,String message) {
        this.status = status;
        this.message = List.of(message);
    }
    public BusinessException(HttpStatus status,List<String> message) {
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public List<String> getErrors() {
        return message;
    }
}

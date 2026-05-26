package com.leets.assignment.global.exception.code;

public interface BaseErrorCode {
    String getCode();
    String getMessage();
    org.springframework.http.HttpStatus getHttpStatus();
}
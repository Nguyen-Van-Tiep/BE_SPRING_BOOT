package com.example.demo.model;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    public int getCode();

    public String getMessage();

    HttpStatus getHttpStatus();
}

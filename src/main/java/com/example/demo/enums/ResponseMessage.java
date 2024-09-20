package com.example.demo.enums;

public enum ResponseMessage {
    SUCCESS("Success"),
    FAIL("Fail"),
    ERROR("Error"),
    NOT_FOUND("Not Found"),
    EXIST("Exist"),
    NOT_EXIST("Not Exist"),
    INVALID("Invalid"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    BAD_REQUEST("Bad Request"),
    SERVER_ERROR("Server Error");

    private final String value;

    ResponseMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

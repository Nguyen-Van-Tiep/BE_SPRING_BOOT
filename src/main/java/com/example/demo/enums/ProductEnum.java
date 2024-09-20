package com.example.demo.enums;

public enum ProductEnum {
    ACTIVE("A"),
    INACTIVE("I"),
    OUT_OF_STOCK("O"),
    DELETED("D");

    private final String value;

    ProductEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

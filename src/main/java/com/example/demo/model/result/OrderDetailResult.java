package com.example.demo.model.result;

import lombok.Data;

@Data
public class OrderDetailResult {
    private String stepCode;
    private String stepName;
    private String stepDate;
    private String description;
    private String icon;
    private String username;

}

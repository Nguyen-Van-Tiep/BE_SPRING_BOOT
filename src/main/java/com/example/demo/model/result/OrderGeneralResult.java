package com.example.demo.model.result;

import lombok.Data;

@Data
public class OrderGeneralResult {
    private String stepName;
    private int stepCode;
    private String timeStep;
    private String icon;
    private String status;
    private String username;
}

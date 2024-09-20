package com.example.demo.model.request;

import lombok.Data;

@Data
public class ApproveBillRequest {
    private int id;
    private String status;
    private String reason;
}

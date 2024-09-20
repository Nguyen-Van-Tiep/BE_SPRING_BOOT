package com.example.demo.model.request;

import lombok.Data;

@Data
public class GetBillRequest {
    private String keyword;
    private String status;
}

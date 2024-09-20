package com.example.demo.model.request;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private String code;
    private String paymentMethod;
    private Integer paymentMethodId;
    private Integer addressCode;
    private String addressMethod;
}

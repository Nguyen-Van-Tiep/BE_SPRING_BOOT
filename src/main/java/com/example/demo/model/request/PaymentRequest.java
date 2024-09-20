package com.example.demo.model.request;

import com.example.demo.model.result.PaymentResult;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private String code;
    private String paymentMethod;
    private Integer addressCode;
    private List<PaymentResult> results;
    private List<Integer> cartIds;
    private String note;
    private String type;
}

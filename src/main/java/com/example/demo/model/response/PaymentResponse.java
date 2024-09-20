package com.example.demo.model.response;

import com.example.demo.model.result.PaymentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String code;
    private BigDecimal price;
    private BigDecimal fee;
    private List<PaymentResult> results;
}

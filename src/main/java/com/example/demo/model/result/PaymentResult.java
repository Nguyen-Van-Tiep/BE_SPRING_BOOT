package com.example.demo.model.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResult {
    private int id;
    private int detailProductId;
    private long quantity;
    private BigDecimal price;
    private String name;
    private String image;
    private String size;
    private String brand;
}

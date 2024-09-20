package com.example.demo.model.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubOrderResult {
    private Integer productId;
    private String productImage;
    private String productName;
    private String size;
    private double quantity;
    private BigDecimal price;
    private String productCode;
}

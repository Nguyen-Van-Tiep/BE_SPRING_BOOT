package com.example.demo.model.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String productName;
    private Long soldQuantity;
    private BigDecimal price;

    public ProductDTO(String productName, Long soldQuantity, BigDecimal price) {
        this.productName = productName;
        this.soldQuantity = soldQuantity;
        this.price = price;
    }
}

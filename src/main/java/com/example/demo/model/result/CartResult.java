package com.example.demo.model.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartResult {
    private Integer detailCartId;
    private Integer detailProductId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private Integer total;
    private String image;
    private String size;
    private String brand;
}

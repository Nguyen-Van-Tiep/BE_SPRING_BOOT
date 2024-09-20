package com.example.demo.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSalesDTO {
    private Integer productId;
    private Double totalQuantity;
    private BigDecimal totalRevenue;
    private String productName;
    private Long salesCount;

    public ProductSalesDTO(Integer productId, Long salesCount) {
        this.productId = productId;
        this.salesCount = salesCount;
    }

    public ProductSalesDTO(Integer productId, Double totalQuantity, BigDecimal totalRevenue) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    public ProductSalesDTO(Integer productId, Double totalQuantity, BigDecimal totalRevenue, String productName, Long salesCount) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
        this.productName = productName;
        this.salesCount = salesCount;
    }

    public ProductSalesDTO(Integer productId, String productName, Long salesCount, BigDecimal totalRevenue) {
        this.productId = productId;
        this.totalRevenue = totalRevenue;
        this.productName = productName;
        this.salesCount = salesCount;
    }
}

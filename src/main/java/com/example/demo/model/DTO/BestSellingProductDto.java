package com.example.demo.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BestSellingProductDto {
    private String productName;
    private Long totalSold;

    public BestSellingProductDto(String productName, Long totalSold) {
        this.productName = productName;
        this.totalSold = totalSold;
    }
}

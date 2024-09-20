package com.example.demo.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class MonthlyRevenueDto {
    private int month;
    private BigDecimal totalRevenue;

    public MonthlyRevenueDto(int month, BigDecimal totalRevenue) {
        this.month = month;
        this.totalRevenue = totalRevenue;
    }
}

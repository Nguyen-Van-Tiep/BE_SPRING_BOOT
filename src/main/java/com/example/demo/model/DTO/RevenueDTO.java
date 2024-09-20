package com.example.demo.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RevenueDTO {
    private Integer year;
    private Integer month;
    private Integer day;
    private BigDecimal totalRevenue;

    public RevenueDTO(Integer month, Integer year, BigDecimal totalRevenue) {
        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public RevenueDTO(Integer year, BigDecimal totalRevenue) {
        this.year = year;
        this.totalRevenue = totalRevenue;
    }
}

package com.example.demo.model.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsDto {
    private Long totalBills;
    private BigDecimal totalAmount;
    private Long successOrders;
    private Long canceledOrders;
    private Long returnedOrders;
    private Double revenue;

}

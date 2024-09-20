package com.example.demo.model.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsResult {
    private Long totalBills;
    private BigDecimal totalAmount;
    private Long successOrders;
    private Long canceledOrders;
    private Long returnedOrders;
    //private Double revenue;
    public StatisticsResult(){

    }
    public StatisticsResult(Long totalBills, BigDecimal totalAmount, Long successOrders, Long canceledOrders, Long returnedOrders) {
        this.totalBills = totalBills;
        this.totalAmount = totalAmount;
        this.successOrders = successOrders;
        this.canceledOrders = canceledOrders;
        this.returnedOrders = returnedOrders;
        //this.revenue = revenue;
    }
}

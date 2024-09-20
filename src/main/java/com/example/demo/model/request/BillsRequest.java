package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillsRequest {
    private String code;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String startDate;
    private String endDate;
    private Integer page;
    private Integer limit;
    private String numberPhone;
    private String customerName;
    private String keyword;
}

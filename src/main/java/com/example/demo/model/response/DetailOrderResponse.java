package com.example.demo.model.response;

import com.example.demo.model.result.OrderDetailResult;
import com.example.demo.model.result.OrderGeneralResult;
import com.example.demo.model.result.SubOrderResult;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailOrderResponse {
    private int id;
    private String orderCode;
    private List<OrderGeneralResult> generals;
    private List<OrderDetailResult> details;
    private List<SubOrderResult> products;
    private String username;
    private String fullName;
    private String numberPhone;
    private String fullAddress;
    private String status;
    private BigDecimal price;
    private BigDecimal sumPrice;
    private BigDecimal fee;
    private String paymentMethod;
    private String statusCode;
    private String receiveDate;
}

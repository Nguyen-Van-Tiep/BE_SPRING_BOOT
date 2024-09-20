package com.example.demo.model.response;

import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.result.CartResult;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartsResponse {
    private Integer cartId;
    private String username;
    private Double total;
    private BigDecimal totalMoney;
    private Double quantity;
    private List<CartResult> carts;
    private PaginationInfo pagination;
}

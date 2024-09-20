package com.example.demo.model.request;

import lombok.Data;

@Data
public class SaveCartRequest {
    private Integer detailCartId;
    private Integer detailProductId;
    private Integer quantity;
}

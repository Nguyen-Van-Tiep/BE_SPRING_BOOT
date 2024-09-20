package com.example.demo.model.DTO;

import lombok.Data;

@Data
public class CartDetailDTO {
    private Integer id;
    private Long userId;
    private Integer productDetailId;
    private Integer quantity;
}

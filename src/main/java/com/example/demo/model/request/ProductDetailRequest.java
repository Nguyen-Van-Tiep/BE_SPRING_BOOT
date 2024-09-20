package com.example.demo.model.request;

import com.example.demo.entity.Color;
import com.example.demo.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private Integer proID;
    private Size size;
    private Color color;
    private Integer status;
}

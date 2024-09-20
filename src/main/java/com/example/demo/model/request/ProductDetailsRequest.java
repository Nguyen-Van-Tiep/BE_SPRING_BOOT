package com.example.demo.model.request;

import com.example.demo.entity.Color;
import com.example.demo.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsRequest {
    private Integer id;
    private Size size;
    private Color color;
    private long quantity;
    private BigDecimal price;
    private String description;
    private int status;
    private List<ImageRequest> images;
}

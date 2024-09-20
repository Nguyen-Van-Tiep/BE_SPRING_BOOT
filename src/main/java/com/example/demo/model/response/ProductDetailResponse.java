package com.example.demo.model.response;

import com.example.demo.entity.Image;
import com.example.demo.entity.ProductDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailResponse {
    private ProductDetail productDetail;
    private List<Image> images;
}
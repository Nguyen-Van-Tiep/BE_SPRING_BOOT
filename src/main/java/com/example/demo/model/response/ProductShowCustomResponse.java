package com.example.demo.model.response;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductShowCustomResponse {
    Product product;
    List<ProductDetail> productDetailList;
}

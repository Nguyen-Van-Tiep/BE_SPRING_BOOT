package com.example.demo.model.response;

import com.example.demo.entity.ProductDetail;
import com.example.demo.model.info.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse {
    private List<ProductDetail> productDetails;
    private PaginationInfo pagination;
}

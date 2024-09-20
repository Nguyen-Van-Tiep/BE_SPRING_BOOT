package com.example.demo.model.response;

import com.example.demo.entity.Product;
import com.example.demo.model.info.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private List<Product> products;
    private PaginationInfo pagination;
}

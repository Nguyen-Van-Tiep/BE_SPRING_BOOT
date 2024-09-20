package com.example.demo.model;

import lombok.Data;

@Data
public class GetProductResponse {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer price;
    private Integer quantity;
    private Integer categoryId;
    private String categoryName;
    private String createdDate;
    private String createdBy;
    private String updatedDate;
    private String updatedBy;
    private Integer status;
    private Integer total;
    private Integer productDetailId;
    private Integer quantityDetail;
    private Integer totalDetail;
    private String createdDateDetail;
    private String createdByDetail;
    private String updatedDateDetail;
    private String updatedByDetail;
    private Integer statusDetail;
}

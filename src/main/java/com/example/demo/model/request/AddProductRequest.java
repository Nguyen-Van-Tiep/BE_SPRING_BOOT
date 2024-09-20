package com.example.demo.model.request;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Material;
import com.example.demo.entity.Sole;
import com.example.demo.entity.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private int id;
    private String productName;
    private Brand brand;
    private Style style;
    private Material material;
    private Sole sole;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private int status;
    private List<ProductDetailsRequest> productDetails;
}

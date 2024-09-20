package com.example.demo.model.request;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Material;
import com.example.demo.entity.Sole;
import com.example.demo.entity.Style;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetProductRequest {
    private String name;
    private Double prices;
    private List<Style> styles;
    private List<Brand> brands;
    private List<Sole> soles;
    private List<Material> materials;
}

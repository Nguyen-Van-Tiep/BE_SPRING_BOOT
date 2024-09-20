package com.example.demo.model.response;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Material;
import com.example.demo.model.info.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class MaterialResponse {
    private List<Material> materials;
    private PaginationInfo pagination;
}

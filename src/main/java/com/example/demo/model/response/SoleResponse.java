package com.example.demo.model.response;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Sole;
import com.example.demo.model.info.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class SoleResponse {
    private List<Sole> soles;
    private PaginationInfo pagination;
}

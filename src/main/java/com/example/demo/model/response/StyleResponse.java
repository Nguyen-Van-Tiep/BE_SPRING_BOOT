package com.example.demo.model.response;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Style;
import com.example.demo.model.info.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class StyleResponse {
    private List<Style> styles;
    private PaginationInfo pagination;
}

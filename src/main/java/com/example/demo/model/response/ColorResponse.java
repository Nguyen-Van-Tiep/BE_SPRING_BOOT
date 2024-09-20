package com.example.demo.model.response;

import com.example.demo.entity.Color;
import com.example.demo.model.info.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class ColorResponse {
    private List<Color> colors;
    private PaginationInfo pagination;
}

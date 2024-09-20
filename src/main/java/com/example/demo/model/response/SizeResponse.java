package com.example.demo.model.response;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Size;
import com.example.demo.model.info.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class SizeResponse {
    private List<Size> sizes;
    private PaginationInfo pagination;
}

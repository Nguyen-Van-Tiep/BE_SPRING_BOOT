package com.example.demo.model.info;

import lombok.Data;

import java.util.List;

@Data
public class PaginationInfo {
    private int pageNumber;
    private int pageSize;
    private int pageTotal;
    private int pageFirst;
    private int pageLast;
    private List<Integer> pages;
    private int pageCount;
}

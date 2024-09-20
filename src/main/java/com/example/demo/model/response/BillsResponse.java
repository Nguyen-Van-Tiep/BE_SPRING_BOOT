package com.example.demo.model.response;

import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.result.BillResult;
import lombok.Data;

import java.util.List;

@Data
public class BillsResponse {
    private List<BillResult> bills;
    private PaginationInfo pagination;
}

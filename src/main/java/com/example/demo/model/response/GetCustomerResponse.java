package com.example.demo.model.response;

import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.result.CustomerResult;
import lombok.Data;

import java.util.List;

@Data
public class GetCustomerResponse {
    private List<CustomerResult> customers;
    private PaginationInfo pagination;
}

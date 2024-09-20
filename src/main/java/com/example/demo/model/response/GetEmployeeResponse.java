package com.example.demo.model.response;

import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.result.EmployeeResult;
import lombok.Data;

import java.util.List;

@Data
public class GetEmployeeResponse {
    private List<EmployeeResult> employees;
    private PaginationInfo pagination;
}

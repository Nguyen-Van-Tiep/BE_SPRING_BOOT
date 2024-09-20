package com.example.demo.model.response;

import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.result.AddressResult;
import lombok.Data;

import java.util.List;

@Data
public class GetAddressesResponse {
    private List<AddressResult> addresses;
    private PaginationInfo pagination;
}

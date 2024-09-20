package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.ApproveBillRequest;
import com.example.demo.model.request.GetBillRequest;
import com.example.demo.model.response.BillsResponse;
import com.example.demo.model.response.DetailOrderResponse;

public interface BillService {
    BillsResponse getBills(GetBillRequest request, int page, int size, String sortField, String sortType);

    BillsResponse getCustomerBills(GetBillRequest request, int page, int size, String sortField, String sortType);

    DetailOrderResponse getCustomerOrderDetail(String code) throws BusinessException;

    DetailOrderResponse detail(String code) throws BusinessException;

    boolean init(ApproveBillRequest request, String username) throws BusinessException;

    BillsResponse getBills(String status, String billCode, int page, int size, String sortField, String sortType, String username);
}

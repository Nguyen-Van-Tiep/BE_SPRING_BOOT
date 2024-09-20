package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.CreatePaymentRequest;
import com.example.demo.model.request.PayRequest;
import com.example.demo.model.request.PaymentRequest;
import com.example.demo.model.response.PaymentResponse;
import com.example.demo.model.response.UserInfoResponse;

public interface PaymentService {
    PaymentResponse validate(PaymentRequest request, String username) throws BusinessException;

    PaymentResponse getPayment(String code) throws BusinessException;

    void create(PayRequest request, String username) throws BusinessException;

    PaymentResponse send(PaymentRequest request, String username) throws BusinessException;

    UserInfoResponse getUserInfo(String username) throws BusinessException;
}

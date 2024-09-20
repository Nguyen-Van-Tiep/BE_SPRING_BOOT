package com.example.demo.service;


import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.response.LoginResponse;

public interface AuthService {
    LoginResponse signIn(String authorization) throws BusinessException;
}

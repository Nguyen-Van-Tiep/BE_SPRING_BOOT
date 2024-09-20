package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Size;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.SizeRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.SizeResponse;

import java.util.List;

public interface SizeService {
    SizeResponse getSizes(SizeRequest request, int page, int size) throws BusinessException;
    Size saveSize(Size size) throws BusinessException;

    Size changeStatus(Size size) throws BusinessException;

    Size update(Size size) throws BusinessException;

    List<Size> getAllSizes();
}

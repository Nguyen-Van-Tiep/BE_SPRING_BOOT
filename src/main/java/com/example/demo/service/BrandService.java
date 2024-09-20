package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.response.BrandsResponse;

import java.util.List;

public interface BrandService {

    BrandsResponse getBrands(BrandsRequest request, int page, int size) throws BusinessException;

    Brand saveBrand(Brand brand) throws BusinessException;

    Brand changeStatus(Brand brand) throws BusinessException;

    Brand update(Brand brand) throws BusinessException;

    List<Brand> getAllBrands();

    List<Brand> getAllBrandsForC();

}

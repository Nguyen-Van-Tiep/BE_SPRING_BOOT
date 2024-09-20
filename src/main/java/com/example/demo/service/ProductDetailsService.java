package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.ProductDetail;
import com.example.demo.model.request.ProductDetailRequest;
import com.example.demo.model.response.ProductDetailResponse;
import com.example.demo.model.response.ProductDetailsResponse;

import java.util.List;

public interface ProductDetailsService {
    ProductDetailsResponse getProductDetails(ProductDetailRequest request, int page, int size) throws BusinessException;
    List<ProductDetail> getAllProductDetails();
    ProductDetail saveProductDetails(ProductDetail ProductDetail) throws BusinessException;

    ProductDetail changStatus(Integer pd) throws BusinessException;

    ProductDetailResponse getProductDetail(Integer id) throws BusinessException;

    List<ProductDetail> updateAll(List<ProductDetail> productDetails) throws BusinessException;

    ProductDetail updatePD(Integer idPro,ProductDetail productDetail) throws BusinessException;
}

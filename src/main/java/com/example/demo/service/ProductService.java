package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Product;
import com.example.demo.model.DTO.BestSellingProductDto;
import com.example.demo.model.DTO.ProductSalesDTO;
import com.example.demo.model.DTO.RevenueDTO;
import com.example.demo.model.request.AddProductRequest;
import com.example.demo.model.request.GetProductRequest;
import com.example.demo.model.request.ProductRequest;
import com.example.demo.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductSalesDTO> getTop10Products();
    List<RevenueDTO> getRevenueByDayMonthYear();
    List<RevenueDTO> getRevenueByMonthYear();
    List<RevenueDTO> getRevenueByYear();

    ProductResponse getProducts(ProductRequest request, int page, int size) throws BusinessException;
    AddProductRequest saveProduct(AddProductRequest Product) throws BusinessException;

    Product updateProduct(Product product) throws BusinessException;

    Product getProduct(Integer id) throws BusinessException;

    List<Product> getAllProducts();

    ProductResponse show(GetProductRequest request, int page, int size) throws BusinessException;

    Product changeStatus (Product product)throws BusinessException;

    List<Product> getTop4NewestProducts () throws BusinessException;

    List<Product> getTop4BestSellingProducts();
}

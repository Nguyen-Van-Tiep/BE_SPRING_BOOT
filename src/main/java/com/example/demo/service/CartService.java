package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.CartItem;
import com.example.demo.model.request.CartItemRequest;
import com.example.demo.model.request.CartRequest;

import java.util.List;

public interface CartService {
    List<CartItem> getCart(CartRequest cartRequest) throws BusinessException;
    void saveCart(CartRequest request)throws BusinessException;

    CartItem updateCartItem(CartItemRequest request) throws  BusinessException;

    void deleteCartItem(Integer id) throws BusinessException;
}

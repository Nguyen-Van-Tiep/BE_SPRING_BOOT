package com.example.demo.service;


import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.CreateAddressRequest;
import com.example.demo.model.response.AddressResponse;
import com.example.demo.model.response.AddressResponseSave;
import com.example.demo.model.response.GetAddressesResponse;

import java.util.List;

public interface AddressService {

    AddressResponseSave saveAddress(CreateAddressRequest request, String username) throws BusinessException;

    AddressResponseSave saveAddressID(Long userID, CreateAddressRequest request) throws BusinessException;

    Boolean update(Integer id, CreateAddressRequest request) throws BusinessException;

    void delete(Integer id) throws BusinessException;

    void setDefaultAddress(Integer id) throws BusinessException;

    void setDefaultAddressID(Integer id, CreateAddressRequest request, Long userID) throws BusinessException;

    AddressResponse getAddress(Integer id) throws BusinessException;

    AddressResponse createAddress(CreateAddressRequest request) throws BusinessException;

    GetAddressesResponse getAddresses(int page, int size) throws BusinessException;

    GetAddressesResponse getAddressesByUserId(Long id, int page, int size) throws BusinessException;

    List<AddressResponse> getAllAddressesByUser() throws BusinessException;
}

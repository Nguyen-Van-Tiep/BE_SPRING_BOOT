package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Sole;
import com.example.demo.model.request.SoleRequest;
import com.example.demo.model.response.SoleResponse;

import java.util.List;

public interface SoleService {
    SoleResponse getSoles(SoleRequest request, int page, int size) throws BusinessException;
    Sole saveSole(Sole Sole) throws BusinessException;

    Sole changeStatus(Sole sole) throws BusinessException;

    Sole update(Sole sole) throws BusinessException;

    List<Sole> getAllSoles();
    List<Sole> getAllSolesForC();
}

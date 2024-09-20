package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Material;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.MaterialRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.MaterialResponse;

import java.util.List;

public interface MaterialService {
    MaterialResponse getMaterials(MaterialRequest request, int page, int size) throws BusinessException;

    Material saveMaterial(Material material) throws BusinessException;

    Material changeStatus(Material id) throws BusinessException;

    Material update(Material material) throws BusinessException;

    List<Material> getAllMaterials();
    List<Material> getAllMaterialsForC();
}

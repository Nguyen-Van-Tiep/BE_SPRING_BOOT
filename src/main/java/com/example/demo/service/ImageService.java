package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Image;

import java.util.List;

public interface ImageService {
    //    BrandsResponse getMaterials(BrandsRequest request, int page, int size) throws BusinessException;
    Image saveImage(Image img) throws BusinessException;

    Image deleteImage(Integer id) throws BusinessException;

    Image getImage(Integer id) throws BusinessException;

    List<Image> getAllImages();
}

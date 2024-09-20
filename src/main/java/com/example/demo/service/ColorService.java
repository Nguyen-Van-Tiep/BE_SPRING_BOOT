package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Color;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.ColorRequest;
import com.example.demo.model.response.BrandsResponse;
import com.example.demo.model.response.ColorResponse;

import java.util.List;

public interface ColorService {
    ColorResponse getColors(ColorRequest request, int page, int size) throws BusinessException;
    Color saveColor(Color color) throws BusinessException;

    Color changeStatus(Color id) throws BusinessException;

    Color update(Color color) throws BusinessException;

    List<Color> getAllColors();
}

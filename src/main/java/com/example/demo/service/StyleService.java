package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Style;
import com.example.demo.model.request.StyleRequest;
import com.example.demo.model.response.StyleResponse;

import java.util.List;

public interface StyleService {
    StyleResponse getStyles(StyleRequest request, int page, int size) throws BusinessException;

    Style saveStyle(Style Style) throws BusinessException;

    Style changeStatus(Style style) throws BusinessException;

    Style update(Style style) throws BusinessException;

    List<Style> getAllStyles();

    List<Style> getAllStylesForC();
}


package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Color;
import com.example.demo.entity.Style;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.StyleRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.StyleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/style")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class StyleController {
    @Autowired
    private StyleService styleService;

    @PostMapping("/get-styles")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody StyleRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.getStyles(request, page, size)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> save(@Valid @RequestBody Style request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.saveStyle(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Style request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Style request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.update(request)));
    }

    @GetMapping("/getAllStyle")
    public ResponseEntity<ResponseData<Object>> getAllBrand() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.getAllStyles()));
    }

    @GetMapping("/getAllStyles")
    public ResponseEntity<ResponseData<Object>> getAllStyles() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(styleService.getAllStylesForC()));
    }
}
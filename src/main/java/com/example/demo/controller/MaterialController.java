package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Color;
import com.example.demo.entity.Material;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.MaterialRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/material")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/get-materials")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody MaterialRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.getMaterials(request, page, size)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> save(@Valid @RequestBody Material request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.saveMaterial(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Material request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Material request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.update(request)));
    }

    @GetMapping("/getAllMaterial")
    public ResponseEntity<ResponseData<Object>> getAllBrand() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.getAllMaterials()));
    }

    @GetMapping("/getAllMaterials")
    public ResponseEntity<ResponseData<Object>> getAllMate() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(materialService.getAllMaterialsForC()));
    }

}

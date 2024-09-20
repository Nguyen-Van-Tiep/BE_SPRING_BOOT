package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Color;
import com.example.demo.entity.Sole;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.SoleRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.SoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/sole")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SoleController {
    @Autowired
    private SoleService soleService;

    @PostMapping("/get-soles")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody SoleRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.getSoles(request, page, size)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> save(@Valid @RequestBody Sole request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.saveSole(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Sole request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Sole request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.update(request)));
    }

    @GetMapping("/getAllSole")
    public ResponseEntity<ResponseData<Object>> getAllBrand() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.getAllSoles()));
    }

    @GetMapping("/getAllSoles")
    public ResponseEntity<ResponseData<Object>> getAllSoles() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(soleService.getAllSolesForC()));
    }


}

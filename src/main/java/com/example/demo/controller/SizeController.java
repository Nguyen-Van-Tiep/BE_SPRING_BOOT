package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Color;
import com.example.demo.entity.Size;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.SizeRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/sizes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @PostMapping("/get-sizes")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody SizeRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(sizeService.getSizes(request, page, size)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> save(@Valid @RequestBody Size request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(sizeService.saveSize(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Size request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(sizeService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Size request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(sizeService.update(request)));
    }

    @GetMapping("/getAllSizes")
    public ResponseEntity<ResponseData<Object>> getAllSize() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(sizeService.getAllSizes()));
    }


}

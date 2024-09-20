package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.entity.Color;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.request.ColorRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/colors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ColorController {
    @Autowired
    private ColorService colorService;

    @PostMapping("/get-colors")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody ColorRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(colorService.getColors(request, page, size)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> save(@Valid @RequestBody Color request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(colorService.saveColor(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Color request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(colorService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Color request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(colorService.update(request)));
    }

    @GetMapping("/getAllColors")
    public ResponseEntity<ResponseData<Object>> getAllSize() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(colorService.getAllColors()));
    }


}
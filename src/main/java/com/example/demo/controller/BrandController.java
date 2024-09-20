package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Brand;
import com.example.demo.model.request.BrandsRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/auth/brand")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandController extends BaseController {
    @Autowired
    private BrandService brandService;

    @PostMapping("/get-brands")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody BrandsRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.getBrands(request, page, size)));
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> saveBrand(@Valid @RequestBody Brand request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.saveBrand(request)));
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<ResponseData<Object>> change(@Valid @RequestBody Brand request) throws BusinessException {
        System.out.println(request.getStatus());
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.changeStatus(request)));
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@Valid @RequestBody Brand request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.update(request)));
    }

    @GetMapping("/getAllBrand")
    public ResponseEntity<ResponseData<Object>> getAllBrand() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.getAllBrands()));
    }

    @GetMapping("/getAllBrands")
    public ResponseEntity<ResponseData<Object>> getAllBrands() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(brandService.getAllBrandsForC()));
    }
}

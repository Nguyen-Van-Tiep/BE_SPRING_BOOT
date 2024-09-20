package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.ProductDetail;
import com.example.demo.model.request.ProductDetailRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/auth/productDetail")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductDetailController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/getAllDetails")
    public ResponseEntity<ResponseData<Object>> getAllDetails() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.getAllProductDetails()));
    }

    @PostMapping("/getDetails")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody ProductDetailRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.getProductDetails(request, page, size)));
    }

    @PutMapping("/update-productDetails")
    public ResponseEntity<ResponseData<Object>> updateAll(@RequestBody List<ProductDetail> detailsList) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.updateAll(detailsList)));
    }

    @PostMapping("/getDetail")
    public ResponseEntity<ResponseData<Object>> getDetail(@RequestBody Integer id) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.getProductDetail(id)));
    }

    @PutMapping("/update-productDetail/{id}")
    public ResponseEntity<ResponseData<Object>> updatePD(@PathVariable("id") Integer idPro,
            @RequestBody ProductDetail detail) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.updatePD(idPro,detail)));
    }

    @PutMapping("/changStatusPD/{id}")
    public ResponseEntity<ResponseData<Object>> changStatusPD(@PathVariable("id")Integer id) throws BusinessException {
        System.out.println(id);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productDetailsService.changStatus(id)));
    }
}

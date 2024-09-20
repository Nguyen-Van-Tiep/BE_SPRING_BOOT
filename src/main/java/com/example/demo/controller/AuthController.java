package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0/auth")
@RequiredArgsConstructor
public class AuthController{
    @Autowired
    private final AuthService authService;

    //    controller cung cấp api cho tùng chức năng
    @GetMapping("/login")
    public ResponseEntity<ResponseData<Object>> authenticate(
            @RequestHeader(name = "Authorization", required = true) String authorization) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(authService.signIn(authorization)));
    }


}

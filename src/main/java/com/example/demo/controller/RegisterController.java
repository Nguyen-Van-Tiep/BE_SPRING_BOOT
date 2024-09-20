package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.SaveUserRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1.0")
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<Object>> registerCustomer(@Valid @RequestBody SaveUserRequest request) throws BusinessException {
        String authorication = userService.createCustomer(request);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(authService.signIn(authorication)));
    }
    @PostMapping("/create-user")
    public ResponseEntity<ResponseData<Object>> createUser(@Valid @RequestBody CreateUserRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.createUserCustomer(request)));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
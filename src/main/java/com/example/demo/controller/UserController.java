package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.*;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/sign-in")
    public ResponseData<Object> signIn(@Valid @RequestBody SaveUserRequest request) {
        return new ResponseData<>().success(request);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseData<Object>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.resetPassword(request, this.getUsername())));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseData<Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.forgotPassword(request, this.getUsername())));
    }

    @PutMapping("/update/personal-information-management")
    public ResponseEntity<ResponseData<Object>> updatePersonal(@Valid @RequestBody UpdatePersonalInformationManagementRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.update(request)));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<ResponseData<Object>> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.updateUser(id, request)));
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseData<Object>> getUserInfo() throws BusinessException {
        UserDisplay user = userService.getUserInfo(this.getUsername());
        return ResponseEntity.ok(new ResponseData<>().success(user));
    }
}

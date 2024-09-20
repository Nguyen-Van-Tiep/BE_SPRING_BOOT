package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.enums.ResponseMessage;
import com.example.demo.model.DTO.CustomerDto;
import com.example.demo.model.DTO.EmployeeDTO;
import com.example.demo.model.request.CustomerRequest;
import com.example.demo.model.request.EmployeeRequest;
import com.example.demo.model.request.SaveCustomerRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all/customer")
    public ResponseEntity<ResponseData<Object>> getAllCustomer() throws BusinessException {
        List<CustomerDto> dtoList = userService.getAllCustomers();
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(dtoList));
    }


    @GetMapping("/get-all/employee")
    public ResponseEntity<ResponseData<Object>> getAllEmployee() throws BusinessException {
        List<EmployeeDTO> dtoList = userService.getAllEmployees();
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(dtoList));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseData<Object>> getUserDetails(@PathVariable Long userId) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.getUserById(userId)));
    }

    @PostMapping("/get-customer")
    public ResponseEntity<ResponseData<Object>> getAll(@RequestBody CustomerRequest request,
                                                       @RequestParam(defaultValue = "0", required = false) int page,
                                                       @RequestParam(defaultValue = "10", required = false) int size,
                                                       @RequestParam("sortField") String sortField,
                                                       @RequestParam("sortOrder") String sortType

    ) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.getCustomer(request, page, size, sortField, sortType)));
    }

    @PostMapping("/get-employee")
    public ResponseEntity<ResponseData<Object>> getCMM(@RequestBody EmployeeRequest request,
                                                       @RequestParam(defaultValue = "0", required = false) int page,
                                                       @RequestParam(defaultValue = "10", required = false) int size,
                                                       @RequestParam("sortField") String sortField,
                                                       @RequestParam("sortOrder") String sortType

    ) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 10) {
            size = 10;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(userService.getEmployee(request, page, size, sortField, sortType)));
    }

    @GetMapping("/customer/statuses")
    public ResponseEntity<ResponseData<Object>> getCustomerStatuses() {
        List<String> statuses = userService.getCustomerStatuses();
        return ResponseEntity.ok().body(new ResponseData<>().success(statuses));
    }

    @PostMapping("/create-employee")
    public ResponseEntity<ResponseData<Object>> registerCustomer(@Valid @RequestBody SaveCustomerRequest request) throws BusinessException {
        userService.createEmployee(request);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(ResponseMessage.SUCCESS.getValue()));
    }


    @PostMapping("/update-status-user/{id}")
    public ResponseEntity<ResponseData<Object>> updateStatusUser(@PathVariable Long id, @RequestParam String status) throws BusinessException {
        userService.updateStatusUser(id, status);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(ResponseMessage.SUCCESS.getValue()));
    }

}



package com.example.demo.service;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.User;
import com.example.demo.model.DTO.CustomerDto;
import com.example.demo.model.DTO.EmployeeDTO;
import com.example.demo.model.info.UserInfo;
import com.example.demo.model.request.*;
import com.example.demo.model.response.GetCustomerResponse;
import com.example.demo.model.response.GetEmployeeResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserInfo> findUserByUsername(String username);

    Boolean resetPassword(ResetPasswordRequest request, String username) throws BusinessException;

    Boolean forgotPassword(ForgotPasswordRequest request, String username) throws BusinessException;

    Boolean update(UpdatePersonalInformationManagementRequest request) throws BusinessException;

    Boolean updateUser(Long id, UpdateUserRequest request) throws BusinessException;

    UserDisplay getUserInfo(String username) throws BusinessException;

    List<CustomerDto> getAllCustomers();

    List<EmployeeDTO> getAllEmployees();

    User getUserById(Long userId) throws BusinessException;

    String createCustomer(SaveUserRequest userRequest) throws BusinessException;

    String createUserCustomer(CreateUserRequest userRequest) throws BusinessException;

    String createEmployee(SaveCustomerRequest userRequest) throws BusinessException;

    List<String> getCustomerStatuses();

    GetCustomerResponse getCustomer(CustomerRequest request, int page, int size, String sortField, String sortType) throws BusinessException;

    GetEmployeeResponse getEmployee(EmployeeRequest request, int page, int size, String sortField, String sortType) throws BusinessException;

    void updateStatusUser(Long id, String status) throws BusinessException;
}

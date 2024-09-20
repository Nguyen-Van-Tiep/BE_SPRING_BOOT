package com.example.demo.service.implement;


import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.CreateAddressRequest;
import com.example.demo.model.response.AddressResponse;
import com.example.demo.model.response.AddressResponseSave;
import com.example.demo.model.response.GetAddressesResponse;
import com.example.demo.model.result.AddressResult;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.SercurityUtils;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public AddressResponse createAddress(CreateAddressRequest request) throws BusinessException {
        Address address = new Address();
        String username = SercurityUtils.getCurrentUser();
        address.setFullName(request.getFullName());
        address.setAddress(request.getAddress());
        address.setFullAddress(request.getFulladdress());
        address.setNumberPhone(request.getNumberPhone());
        address.setProvinceID(request.getProvinceID());
        address.setProvinceName(request.getProvinceName());
        address.setWardCode(request.getWardCode());
        address.setWardName(request.getWardName());
        address.setDistrictID(request.getDistrictID());
        address.setDistrictName(request.getDistrictName());
        address.setAddressType(request.getAddressType());
        address.setIsDefault(request.getIsDefault());
        address.setCreatedBy(username);
        address.setModifiedBy(username);
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            resetAllDefaultAddresses(username, request.getId());
        }
        Address savedAddress = addressRepository.save(address);
        AddressResponse response = AddressResponse.builder()
                .numberPhone(savedAddress.getNumberPhone())
                .address(savedAddress.getAddress())
                .fullName(savedAddress.getFullName())
                .build();
        return response;
    }

    @Override
    public AddressResponseSave saveAddress(CreateAddressRequest request, String username) throws BusinessException {
        Address address = new Address();
        if (request.getId() == 0) {
            address.setCreatedBy(username);
        } else {
            address = addressRepository.findById(request.getId()).orElse(null);
            if (address == null) {
                throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
            }
        }
        address.setFullName(request.getFullName());
        address.setAddress(request.getAddress());
        address.setFullAddress(request.getFulladdress());
        address.setNumberPhone(request.getNumberPhone());
        address.setProvinceID(request.getProvinceID());
        address.setProvinceName(request.getProvinceName());
        address.setWardCode(request.getWardCode());
        address.setWardName(request.getWardName());
        address.setDistrictID(request.getDistrictID());
        address.setDistrictName(request.getDistrictName());
        address.setAddressType(request.getAddressType());
        address.setIsDefault(request.getIsDefault());
        address.setModifiedBy(username);
        address = addressRepository.save(address);
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            resetAllDefaultAddresses(username,address.getId());
        }
        return convertToResponse(address);
    }

    @Override
    public AddressResponseSave saveAddressID(Long userID, CreateAddressRequest request) throws BusinessException {
        User user = userRepository.findById(userID).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Address address = new Address();
        // Kiểm tra và xử lý khi request.getId() là 0 (tức là thêm mới địa chỉ)
        if (request.getId() == 0  ) {
            address.setCreatedBy(user.getUsername());
            address.setUser(user);
        } else {
            // Kiểm tra và lấy thông tin address từ request.getId()
            address = addressRepository.findById(request.getId())
                     .orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
        }
        address.setFullName(request.getFullName());
        address.setAddress(request.getAddress());
        address.setFullAddress(request.getFulladdress());
        address.setNumberPhone(request.getNumberPhone());
        address.setProvinceID(request.getProvinceID());
        address.setProvinceName(request.getProvinceName());
        address.setWardCode(request.getWardCode());
        address.setWardName(request.getWardName());
        address.setDistrictID(request.getDistrictID());
        address.setDistrictName(request.getDistrictName());
        address.setAddressType(request.getAddressType());
        address.setIsDefault(request.getIsDefault());
        address.setModifiedBy(user.getUsername());
        address = addressRepository.save(address);
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            resetAllDefaultAddresses(user.getUsername(), address.getId());
        }
        return convertToResponse(address);
    }

    private AddressResponseSave convertToResponse(Address address) {
        AddressResponseSave response = new AddressResponseSave();
        response.setFullName(address.getFullName());
        response.setAddress(address.getAddress());
        response.setFulladdress(address.getFulladdress());
        response.setNumberPhone(address.getNumberPhone());
        response.setProvinceID(address.getProvinceID());
        response.setProvinceName(address.getProvinceName());
        response.setWardCode(address.getWardCode());
        response.setWardName(address.getWardName());
        response.setDistrictID(address.getDistrictID());
        response.setDistrictName(address.getDistrictName());
        response.setAddressType(address.getAddressType());
        response.setIsDefault(address.getIsDefault());
        return response;
    }

    @Override
    public Boolean update(Integer id, CreateAddressRequest request) throws BusinessException {
        String username = SercurityUtils.getCurrentUser();
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
        address.setFullName(request.getFullName());
        address.setNumberPhone(request.getNumberPhone());
        address.setProvinceID(request.getProvinceID());
        address.setProvinceName(request.getProvinceName());
        address.setWardCode(request.getWardCode());
        address.setWardName(request.getWardName());
        address.setDistrictID(request.getDistrictID());
        address.setDistrictName(request.getDistrictName());
        address.setFullAddress(request.getFulladdress());
        address.setAddressType(request.getAddressType());
        address.setCreatedBy(username);
        address.setModifiedBy(username);
        address.setIsDefault(request.getIsDefault());
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            resetAllDefaultAddresses(username,id);
        }
        addressRepository.save(address);
        return true;
    }

    @Override
    @Transactional
    public void delete(Integer id) throws BusinessException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
        addressRepository.delete(address);
    }

    @Override
    public AddressResponse getAddress(Integer id) throws BusinessException {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
        }
        return AddressResponse.builder()
                .numberPhone(address.getNumberPhone())
                .address(address.getAddress())
                .fullName(address.getFullName())
                .id(address.getId())
                .addressType(address.getAddressType())
                .provinceID(address.getProvinceID())
                .provinceName(address.getProvinceName())
                .wardCode(address.getWardCode())
                .wardName(address.getWardName())
                .districtID(address.getDistrictID())
                .districtName(address.getDistrictName())
                .isDefault(address.getIsDefault())
                .build();
    }

    @Override
    public GetAddressesResponse getAddresses(int page, int size) throws BusinessException {
        Pageable pageable = PageRequest.of(page, size);
        GetAddressesResponse response = new GetAddressesResponse();
        String username = SercurityUtils.getCurrentUser();
        Page<Address> addresses = addressRepository.findAllByUserIdOrderByIsDefaultDescModifiedDateDesc(username, pageable);
        List<AddressResult> results = new ArrayList<>();
        for (Address address : addresses.getContent()) {
            AddressResult result = this.convertResponse(address);
            results.add(result);
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(addresses.getNumber(), addresses.getSize(), Math.toIntExact(addresses.getTotalElements()));
        response.setAddresses(results);
        response.setPagination(paginationInfo);
        return response;
    }

    @Override
    public GetAddressesResponse getAddressesByUserId(Long id, int page, int size) throws BusinessException {
        Pageable pageable = PageRequest.of(page, size);
        GetAddressesResponse response = new GetAddressesResponse();
        Page<Address> addresses = addressRepository.findAllUserIdOrderByAddress(id, pageable);
        List<AddressResult> results = new ArrayList<>();
        for (Address address : addresses.getContent()) {
            AddressResult result = this.convertResponse(address);
            results.add(result);
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(addresses.getNumber(), addresses.getSize(), Math.toIntExact(addresses.getTotalElements()));
        response.setAddresses(results);
        response.setPagination(paginationInfo);
        return response;
    }

    @Override
    public List<AddressResponse> getAllAddressesByUser() throws BusinessException {
        String username = SercurityUtils.getCurrentUser();
        List<Address> addresses = addressRepository.findAllByUserIdOrderByIsDefaultDescModifiedDateDesc(username);
        return addresses.stream()
                .map(address -> AddressResponse.builder()
                        .numberPhone(address.getNumberPhone())
                        .address(address.getAddress())
                        .fullName(address.getFullName())
                        .isDefault(address.getIsDefault())
                        .build())
                .collect(Collectors.toList());
    }

    private AddressResult convertResponse(Address address) {
        AddressResult response = new AddressResult();
        response.setAddress(address.getAddress());
        response.setFullName(address.getFullName());
        response.setIsDefault(address.getIsDefault());
        response.setNumberPhone(address.getNumberPhone());
        response.setId(address.getId());
        return response;
    }

    public void resetAllDefaultAddresses(String username, Integer id) {
        addressRepository.resetAllDefaultAddresses(username, id);
    }

    public void resetAllDefaultAddressesID(Long userID, Integer id) {
        addressRepository.resetAllDefaultAddressesID(userID, id);
    }

    @Override
    @Transactional
    public void setDefaultAddress(Integer id) throws BusinessException {
        String username = SercurityUtils.getCurrentUser();
        // Reset all addresses to not default for the user
        resetAllDefaultAddresses(username, id);

        // Set the selected address as default
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
        address.setIsDefault(true);
        addressRepository.save(address);
    }

    @Override
    public void setDefaultAddressID(Integer id, CreateAddressRequest request, Long userID) throws BusinessException {
        Address address = new Address();
        User user = userRepository.findById(userID).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (request.getId() == 0) {
            address.setCreatedBy(user.getUsername());
            address.setUser(user);
        } else {
            address = addressRepository.findById(request.getId()).orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));

        }
        String username = user.getUsername();
        resetAllDefaultAddresses(username, id);
        address.setIsDefault(true);
        addressRepository.save(address);
    }


}



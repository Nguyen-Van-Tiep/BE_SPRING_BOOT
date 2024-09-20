package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.CreateAddressRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/user/account/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> saveAddress(@Valid @RequestBody CreateAddressRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.saveAddress(request, this.getUsername())));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData<Object>> createAddress(@Valid @RequestBody CreateAddressRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.createAddress(request)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<Object>> updateAddress(@Valid @PathVariable("id") Integer id, @RequestBody CreateAddressRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.update(id, request)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Object>> deleteAddress(@Valid @PathVariable("id") Integer id) throws BusinessException {
        addressService.delete(id);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success("deltete successfully"));
    }

    @GetMapping("/get-address/{id}")
    public ResponseEntity<ResponseData<Object>> getAddress(@PathVariable("id") Integer id) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.getAddress(id)));
    }

    @GetMapping("/get-addresses")
    public ResponseEntity<ResponseData<Object>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size

    ) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 5) {
            size = 5;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.getAddresses(page, size)));
    }

    @GetMapping("/set-default/{id}")
    public ResponseEntity<ResponseData<Object>> setDefaultAddress(@PathVariable("id") Integer id) throws BusinessException {
        addressService.setDefaultAddress(id);
        return ResponseEntity.ok().body(new ResponseData<>().success("Address set as default successfully"));
    }

    @GetMapping("/info/set-default/{id}")
    public ResponseEntity<ResponseData<Object>> setDefaultAddressID(@PathVariable("id") Integer id, CreateAddressRequest request, @RequestParam("userID") Long userID) throws BusinessException {
        addressService.setDefaultAddressID(id, request, userID);
        return ResponseEntity.ok().body(new ResponseData<>().success("Address set as default successfully"));
    }

    @GetMapping("/user/{id}/addresses")
    public ResponseEntity<ResponseData<Object>> getAddressesByUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws BusinessException {
        if (page < 0) {
            page = 0;
        }
        if (size <= 5) {
            size = 5;
        }
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.getAddressesByUserId(id, page, size)));
    }

    @PostMapping("/save/{userID}")
    public ResponseEntity<ResponseData<Object>> saveAddressID(@PathVariable Long userID,
                                                              @Valid @RequestBody CreateAddressRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(addressService.saveAddressID(userID, request)));
    }


}

package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.ApproveBillRequest;
import com.example.demo.model.request.GetBillRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/admin/bill")
public class BillController extends BaseController{

    private final BillService billService;

    @PostMapping("get-bills")
    public ResponseEntity<ResponseData<Object>> getBills(@RequestBody GetBillRequest request,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam("sortField") String sortField,
                                                         @RequestParam("sortOrder") String sortType
    ) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(billService.getBills(request, page, size, sortField, sortType)));
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<ResponseData<Object>> detail(@PathVariable String code) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(billService.detail(code)));
    }

    @PostMapping("get-bills/customer")
    public ResponseEntity<ResponseData<Object>> getBillsCustomer(@RequestBody GetBillRequest request,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam("sortField") String sortField,
                                                         @RequestParam("sortOrder") String sortType
    ) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(billService.getCustomerBills(request, page, size, sortField, sortType)));
    }

    @GetMapping("/detail-customer/{code}")
    public ResponseEntity<ResponseData<Object>> detailCustomer(@PathVariable String code) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(billService.getCustomerOrderDetail(code)));
    }

    @PostMapping("/approve")
    public ResponseEntity<ResponseData<Object>> initPayment(@RequestBody ApproveBillRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(billService.init(request, this.getUsername())));
    }
}

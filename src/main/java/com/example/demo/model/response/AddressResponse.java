package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private int id;
    private Long userID;
    private String numberPhone;
    private String address;
    private String fulladdress;
    private String fullName;
    private Boolean isDefault;
    private String addressType;
    private Integer districtID;
    private String districtName;
    private String wardCode;
    private String wardName;
    private Integer provinceID;
    private String provinceName;
}

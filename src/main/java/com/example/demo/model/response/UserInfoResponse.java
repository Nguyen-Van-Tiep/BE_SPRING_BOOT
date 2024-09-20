package com.example.demo.model.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String username;
    private String email;
    private String numberPhone;
    private String address;
    private String avatar;
    private String fullName;
    private Integer districtID;
    private String districtName;
    private String wardCode;
    private String wardName;
    private Integer provinceID;
    private String provinceName;
}

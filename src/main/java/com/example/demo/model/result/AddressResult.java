package com.example.demo.model.result;

import lombok.Data;

@Data
public class AddressResult {
    private int id;
    private String numberPhone;
    private String address;
    private String fullName;
    private Boolean isDefault;
}

package com.example.demo.model.result;

import lombok.Data;

@Data
public class CustomerResult {
    private Long id;
    private String fullName;
    private String numberPhone;
    private String gender;
    private String birthOfDate;
    private String email;
    private String status;
    private String role;
}

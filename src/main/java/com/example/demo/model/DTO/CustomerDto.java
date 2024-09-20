package com.example.demo.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerDto {
    private Long id;
    private String fullName;
    private String numberPhone;
    private String gender;
    private String birthOfDate;
    private String email;
    private String status;
    private String role;
    }

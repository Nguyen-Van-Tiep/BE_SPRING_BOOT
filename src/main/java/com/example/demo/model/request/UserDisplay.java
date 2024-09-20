package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDisplay {
    private String username;
    private String fullName;
    private String numberPhone;
    private String gender;
    private String birthOfDate;
    private String email;
    private String role;

}

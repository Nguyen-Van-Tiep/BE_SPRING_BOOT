package com.example.demo.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {
    @NotBlank(message = "Tài khoản không được để trống")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Tài khoản không hợp lệ")
    private String username;

    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[1-9]|84[1-9])([0-9]{8,9})$", message = "Số điện thoại không hợp lệ")
    private String numberPhone;

    private String gender;
    

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Ngày sinh không hợp lệ")
    private String dateOfBirth;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    private String roles;

    @NotBlank(message = "Mật khẩu cũ không được để trống")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",message = "Mật khẩu không hợp lệ")
    private String password;
    private String status;
}

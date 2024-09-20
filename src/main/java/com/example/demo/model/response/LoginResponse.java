package com.example.demo.model.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String username;
    private String token;
    private String gender;
    private String email;
    private List<String> role;
}

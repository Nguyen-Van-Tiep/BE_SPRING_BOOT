package com.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.JwtDecoder;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
public class BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtDecoder jwtDecoder;
    private static final String TOKEN_PREFIX = "Bearer ";
    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    private static final String AUTHORIZATION = "Authorization";
    protected String getUsername() {
        DecodedJWT decodedJWT = this.getDecodedJWT();
        if (decodedJWT == null){
            return null;
        }
        String username = decodedJWT.getClaim("e").toString().replaceAll("\"", "");;
        return username;
    }

    protected List<String> getRoles() {
        DecodedJWT decodedJWT = this.getDecodedJWT();
        if (decodedJWT == null){
            return Collections.emptyList();
        }
        return CommonUtil.converStringToList(decodedJWT.getClaim("au").toString(), String.class);
    }

    protected LoginResponse getUserLogin(){
        LoginResponse response = new LoginResponse();
        response.setRole(this.getRoles());
        response.setUsername(this.getUsername());
        return response;

    }

    private DecodedJWT getDecodedJWT(){
        String token = request.getHeader(AUTHORIZATION);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String accessToken = token.substring(TOKEN_PREFIX.length());
            if (CommonUtil.isNullOrEmpty(accessToken)){
                return null;
            }
            return jwtDecoder.decode(accessToken);
        }
        return null;
    }
}

package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.UserPrincipal;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.utilities.JwtIssuer;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    /**
     * @param authorization
     * @return
     * @throws BusinessException
     */
    @Override
    public LoginResponse
    signIn(String authorization) throws BusinessException {
        log.info("AuthenticationService - SIGNIN - START - REQUEST: {}", authorization);
        if (authorization == null || !authorization.startsWith("Basic ")) {
            throw new BusinessException(ErrorCode.CHECK_AUTHEN,"Đăng nhập thất bại");
        }
        LoginResponse response = new LoginResponse();
        String token;
        String username = new String(Base64.getDecoder().decode(authorization.substring(6))).split(":")[0].toLowerCase().trim();
        String password = new String(Base64.getDecoder().decode(authorization.substring(6))).split(":")[1].trim();
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED,"Tài khoản hoặc mật khẩu không chính xác");
        }
        List<String> roles = this.roleRepository.getRolesByUser(user.getId());
        if (roles.isEmpty()) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED,"Tài khoản hoặc mật khẩu không chính xác");
        }
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            var authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var principal = (UserPrincipal) authentication.getPrincipal();
            token = jwtIssuer.issue(JwtIssuer.Request.builder()
                    .userId(principal.getId())
                    .username(principal.getUsername())
                    .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .build());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED,"Tài khoản hoặc mật khẩu không chính xác");
        }
        response.setUsername(username);
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setGender(user.getGender());
        response.setRole(roles);
        return response;
    }
    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}

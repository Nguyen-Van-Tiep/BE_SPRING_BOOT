package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String username;

    @JsonIgnore
    private final String password;

    private final List<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // <-- Very important to not forget
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // <-- Very important to not forget
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // <-- Very important to not forget
    }

    @Override
    public boolean isEnabled() {
        return true; // <-- Very important to not forget
    }
}

package com.example.demo.security;

import com.example.demo.entity.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private String email;
    private String password;

    @Getter
    private String state;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(Account account) {
        email = account.getEmail();
        password = account.getPassword();
        authorities = Arrays.stream(account.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        state = account.getState();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

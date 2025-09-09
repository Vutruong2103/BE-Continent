package com.example.continent.application.security;

import com.example.continent.application.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * implements UserDetails: đây là interface chuẩn mà Spring Security sử dụng để quản lý user.
 * User user: là entity trong database của mình
 * Constructor nhận vào một User → để ánh xạ sang dạng UserDetails
 */

public class UserDetailsCustom implements UserDetails {
    private final User user;

    public UserDetailsCustom(User user) {
        this.user = user;
    }

    //Trả về danh sách quyền (role) của user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

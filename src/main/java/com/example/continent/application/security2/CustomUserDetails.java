package com.example.continent.application.security2;

import com.example.continent.application.domain.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Một implementation của UserDetails "bọc" lấy entity User của chúng ta.
 * Điều này cho phép chúng ta truy cập đối tượng User đầy đủ từ Principal của Spring Security.
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Chuyển đổi danh sách Role entities thành danh sách GrantedAuthority.
        // Đây là nơi chúng ta định nghĩa quyền của người dùng dựa trên dữ liệu từ DB.
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        // Mật khẩu được quản lý bởi Keycloak, không dùng trong context này.
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Giả định tài khoản không bao giờ hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Giả định tài khoản không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Giả định credentials không hết hạn
    }

    @Override
    public boolean isEnabled() {
        // Có thể thay bằng một trường `isActive` trong entity User nếu có
        return true;
    }
}

package com.example.continent.application.domain.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Nếu có login thì lấy username từ SecurityContextHolder
 * Ví dụ: return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
 * Trường hợp chưa login: trả về giá trị mặc định
 */
@Component("auditorAware")
public class SimpleAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("system"); // fallback
        }
        return Optional.of(authentication.getName()); // username đăng nhập
    }

}

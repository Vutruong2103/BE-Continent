package com.example.continent.application.domain.config;

import org.springframework.data.domain.AuditorAware;
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
        return Optional.of("system");
    }
}

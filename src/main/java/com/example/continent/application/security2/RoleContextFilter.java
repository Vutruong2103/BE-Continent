package com.example.continent.application.security2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter này chạy sau khi người dùng đã được xác thực bởi Spring Security.
 * Nhiệm vụ của nó là lấy CustomUserDetails từ SecurityContext và đặt nó vào RoleContextHolder
 * để các tầng dưới (service, repository) có thể truy cập dễ dàng.
 */
@Slf4j
@Component
public class RoleContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Lấy đối tượng Authentication sau khi đã được xác thực
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            // Nếu Principal là CustomUserDetails, đặt nó vào context của chúng ta
            log.trace("Populating RoleContextHolder with user: {}", userDetails.getUsername());
            RoleContextHolder.setContext(userDetails);
        }

        try {
            // Tiếp tục chuỗi filter
            filterChain.doFilter(request, response);
        } finally {
            // Luôn dọn dẹp context sau khi request hoàn tất để tránh rò rỉ bộ nhớ
            log.trace("Clearing RoleContextHolder");
            RoleContextHolder.clearContext();
        }
    }
}

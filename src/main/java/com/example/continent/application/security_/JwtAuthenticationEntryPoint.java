package com.example.continent.application.security_;

import com.example.continent.application.request_.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * @TODO : Comment cái này bị thiếu nè
 * 
 * JwtAuthenticationEntryPoint: Xử lý khi người dùng chưa xác thực (chưa đăng nhập) mà cố truy cập tài nguyên bảo vệ.
 * Trả về lỗi 401 Unauthorized với định dạng JSON.
 *  Ai làm ? 
 *  Làm ở đâu ? 
 *  Khi nào ? 
 * Cách này cũng hơi cũ rồi. tìm cách hiện đại lên xíu 
 * 
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        int status = HttpStatus.UNAUTHORIZED.value();
        String message = authException.getMessage() != null
                ? authException.getMessage()
                : "Unauthorized";

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(status)
                .message(message)
                .build();

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}

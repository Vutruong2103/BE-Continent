package com.example.continent.application.rest;

import com.example.continent.application.domain.response.JWTAuthResponse;
import com.example.continent.application.request.ApiResponse;
import com.example.continent.application.request.LoginDto;
import com.example.continent.application.request.LogoutRequest;
import com.example.continent.application.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController xử lý việc đăng nhập và trả về JWT cho client.
 * Client sẽ sử dụng JWT này để xác thực các request tiếp theo.
 * AuthenticationManager: Đối tượng này chịu trách nhiệm xác thực thông tin đăng nhập.
 * JwtService: Lớp này dùng để tạo và xác thực JWT.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthenticationManager authenticationManager;
    JwtService jwtService;

    @PostMapping("/login")

    @Operation(summary = "Đăng nhập nhận JWT")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        String token = jwtService.generateToken(authentication);

        JWTAuthResponse jwt = JWTAuthResponse.builder().token(token).build();
        log.info("token: {}", token);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) {
        jwtService.logout(logoutRequest.getToken());
        return ResponseEntity.ok().build();
    }

}

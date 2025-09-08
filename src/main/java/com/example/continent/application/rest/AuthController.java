package com.example.continent.application.rest;

import com.example.continent.application.domain.response.JWTAuthResponse;
import com.example.continent.application.domain.service.AuthService;
import com.example.continent.application.request.LoginDto;
import com.example.continent.application.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthController {

    AuthenticationManager authenticationManager;
    AuthService authService;
    JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập nhận JWT")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        // Xác thực user (Spring Security sẽ gọi CustomUserDetailsService + check password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        // Sinh token từ Authentication => chứa username + scopes/roles
        String token = jwtService.generateToken(authentication);

        JWTAuthResponse jwt = JWTAuthResponse.builder().token(token).build();
        log.info("token: {}", token);

        return ResponseEntity.ok(jwt);
    }

}

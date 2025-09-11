package com.example.continent.application.security_;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * dùng để xử lý JWT trong ứng dụng Spring Boot.
 * @PostConstruct init(): Khởi tạo key từ chuỗi secret sau khi bean được tạo.
 *
 * getSigningKey(): Tạo và trả về key dùng để ký và xác thực JWT.
 * generateToken(Authentication authentication):
 * Sinh JWT cho user đã xác thực, Lấy username, authorities (quyền) từ user, Đưa quyền vào claim scopes, Thiết lập subject, issuedAt, expiration, ký bằng key bí mật.
 *
 * extractUsername(String token): Lấy username (subject) từ JWT.
 * extractClaim(String token, Function<Claims, T> fn): Hàm tổng quát để lấy bất kỳ claim nào từ JWT.
 * validateToken(String token, String username): Xác thực JWT bằng cách so sánh username trong token với username được truyền vào
 */

@Service
@Slf4j
public class JwtService {

    /**
     * @TODO : Cái này thường để trong file .env hoặc config riêng, không để cứng trong code.
     * Cần nghiên cứu. 
     * Mục đích là để tăng cường bảo mật.
     * Không cần vào coi code mà chỉ cần vào môi trường để check.
     * Dư.
     */
    @Value("${security.jwt.secret:change-me-to-256bit-secret-change-me}")
    private String secret;


    /**
     * @TODO : Tương tự như cái trên. 
     * Dư
     */
    @Value("${security.jwt.expiration-ms:86400000}") // 24h
    private long expirationMs;


    private SecretKey key;

    @PostConstruct
    public void init() {
        // Convert string secret thành key hợp lệ
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expirationMs);

        // Lấy authorities (roles hoặc scopes)
        List<String> scopes = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)   // ví dụ "ROLE_MANAGER"
                .toList();

        // Nhúng scopes vào claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("scopes", scopes);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> fn) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return fn.apply(claims);
    }

    /**
     * @TODO : Cái này cần bổ sung thêm validate signature, expiration nữa.
     * Đưa qua anh Đức là rớt. 
     * Thiếu kiểm tra thời gian hết hạn token.
     * Thiếu kiểm tra chữ ký.
     * Thiếu kiểm tra token bị sửa đổi.
     * Thiếu kiểm tra token bị thu hồi.
     * ...v.v..
     */
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username));
    }

}


package com.example.continent.application.security;

import com.example.continent.application.domain.model.InvalidatedToken;
import com.example.continent.application.domain.repository.InvalidatedTokenRepository;
import com.example.continent.application.request.LogoutRequest;
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
 * JwtService: dùng để xử lý JWT trong ứng dụng Spring Boot.
 * @PostConstruct init(): Khởi tạo key từ chuỗi secret sau khi bean được tạo.
 *
 * @invalidatedTokenRepository: nơi lưu token bị logout
 *
 * getSigningKey(): Tạo và trả về key dùng để ký và xác thực JWT.
 * generateToken(Authentication authentication):
 * Sinh JWT cho user đã xác thực, Lấy username, authorities (quyền) từ user, Đưa quyền vào claim scopes, Thiết lập subject, issuedAt, expiration, ký bằng key bí mật.
 *
 *
 * extractUsername(String token): Lấy username (subject) từ JWT.
 * extractClaim(String token, Function<Claims, T> fn): Hàm tổng quát để lấy bất kỳ claim nào từ JWT.
 * validateToken(String token, String username): Validate token (chưa hết hạn, username đúng, không bị logout)
 */

@Service
@Slf4j
public class JwtService {

    @Value("${security.jwt.secret:change-me-to-256bit-secret-change-me}")
    private String secret;

    @Value("${security.jwt.expiration-ms:86400000}") // 24h
    private long expirationMs;

    private SecretKey key;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public JwtService(InvalidatedTokenRepository invalidatedTokenRepository) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expirationMs);

        List<String> scopes = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)   // ví dụ "ROLE_MANAGER"
                .toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("scopes", scopes);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

     public void logout(String token){
        Claims claims = extractAllClaims(token);

         InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                 .id(claims.getId())
                 .expiryTime(claims.getExpiration())
                 .build();

         invalidatedTokenRepository.save(invalidatedToken);
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

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String username) {
        Claims claims = extractAllClaims(token);

        boolean notExpired = claims.getExpiration().after(new Date());
        boolean notInvalidated = !invalidatedTokenRepository.existsById(claims.getId());

        return (claims.getSubject().equals(username) && notExpired && notInvalidated);
    }
}


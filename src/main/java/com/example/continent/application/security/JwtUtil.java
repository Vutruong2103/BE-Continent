//package com.example.continent.application.security;
//
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.time.Instant;
//import java.util.Collection;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    //ký key token
//    @Value("${app.jwt.secret}")
//    private String secret;
//
//    //thời gian sống của token
//    @Value("${app.jwt.expiration-ms}")
//    private long expirationMs;
//
//    //Tạo key để ký token
//    private Key key() {
//        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }
//
//    //Sinh token
//    public String generateToken(String username, Collection<? extends GrantedAuthority> roles) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("roles", roles.stream().map(GrantedAuthority::getAuthority).toList())
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plusMillis(expirationMs)))
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    //Trích xuất username từ token
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder().setSigningKey(key()).build()/// dùng key bí mật để verify
//                .parseClaimsJws(token).getBody().getSubject();
//    }
//
//    //Kiểm tra token hợp lệ
//    public boolean validate(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//}
//

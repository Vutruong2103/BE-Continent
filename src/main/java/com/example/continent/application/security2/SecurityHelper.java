package com.example.continent.application.security2;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Lớp tiện ích (Helper) để truy cập thông tin bảo mật một cách an toàn từ SecurityContext.
 * Cung cấp các phương thức để lấy thông tin người dùng đang đăng nhập.
 */
@UtilityClass
public class SecurityHelper {

    /**
     * Lấy đối tượng Authentication hiện tại từ SecurityContext.
     *
     * @return Optional chứa Authentication nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * Lấy đối tượng Jwt từ Authentication hiện tại.
     *
     * @return Optional chứa Jwt nếu người dùng được xác thực bằng JWT, ngược lại là Optional rỗng.
     */
    public static Optional<Jwt> getJwt() {
        return getAuthentication()
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .map(JwtAuthenticationToken::getToken);
    }

    /**
     * Lấy User ID (subject) từ JWT của người dùng đang đăng nhập.
     *
     * @return Optional chứa User ID (sub claim) nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<String> getCurrentUserId() {
        return getJwt().map(Jwt::getSubject);
    }

    /**
     * Lấy username (preferred_username claim) từ JWT của người dùng đang đăng nhập.
     *
     * @return Optional chứa username nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<String> getCurrentUsername() {
        return getJwt().map(jwt -> jwt.getClaimAsString("preferred_username"));
    }

    /**
     * Lấy stream các quyền (authorities) của người dùng hiện tại.
     *
     * @return Stream các GrantedAuthority, có thể rỗng.
     */
    private static Stream<String> getAuthorities() {
        return getAuthentication()
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream().map(GrantedAuthority::getAuthority))
                .orElse(Stream.empty());
    }

    /**
     * Kiểm tra xem người dùng hiện tại có một quyền (authority/role) cụ thể hay không.
     *
     * @param authority Tên quyền cần kiểm tra (ví dụ: "ROLE_ADMIN").
     * @return true nếu người dùng có quyền đó, ngược lại là false.
     */
    public static boolean isCurrentUserInRole(String authority) {
        return getAuthorities().anyMatch(authority::equals);
    }

}

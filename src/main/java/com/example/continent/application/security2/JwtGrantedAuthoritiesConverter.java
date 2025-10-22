package com.example.continent.application.security2;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converter tùy chỉnh để trích xuất vai trò (roles) từ JWT của Keycloak và chuyển đổi chúng thành các GrantedAuthority.
 * Keycloak thường nhúng các vai trò trong một claim có tên là 'realm_access'.
 */
@Component
public class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Lấy claim 'realm_access' từ JWT
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_CLAIM);

        if (realmAccess == null || realmAccess.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu không có claim
        }

        // Lấy danh sách các vai trò từ bên trong claim 'realm_access'
        Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);

        if (roles == null || roles.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu không có vai trò nào
        }

        // Chuyển đổi mỗi vai trò thành một SimpleGrantedAuthority với tiền tố "ROLE_"
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
                .collect(Collectors.toList());
    }
}

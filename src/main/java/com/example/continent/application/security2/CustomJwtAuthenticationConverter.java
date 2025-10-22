package com.example.continent.application.security2;

import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Converter này là trái tim của luồng xác thực, hiện thực hóa triết lý "Principal là Domain Entity".
 * Nó chuyển đổi một JWT đã được validate thành một đối tượng Authentication chứa đầy đủ entity User từ database.
 */
@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    private final UserRepository userRepository;

    // Claim trong JWT của Keycloak thường chứa username
    private static final String USERNAME_CLAIM = "preferred_username";

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        // 1. Lấy username từ claim của JWT.
        // Sử dụng một biến final để đảm bảo "effectively final" cho biểu thức lambda.
        final String username = jwt.hasClaim(USERNAME_CLAIM)
                ? jwt.getClaimAsString(USERNAME_CLAIM)
                : jwt.getSubject();

        // 2. Dùng username để truy vấn DB, lấy ra entity User đầy đủ và chưa bị xóa.
        // SỬ DỤNG ĐÚNG TÊN PHƯƠNG THỨC: findByUsernameAndDeletedFalse
        User user = userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username '%s' not found or is deleted in the local database", username)
                ));

        // 3. Tạo đối tượng CustomUserDetails để "bọc" entity User
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // 4. Tạo và trả về một UsernamePasswordAuthenticationToken.
        // Đây là một implementation của Authentication được Spring Security sử dụng rộng rãi.
        // - principal: Đối tượng CustomUserDetails (chứa toàn bộ entity User).
        // - credentials: null, vì chúng ta đã xác thực bằng JWT.
        // - authorities: Danh sách quyền được lấy từ userDetails.
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

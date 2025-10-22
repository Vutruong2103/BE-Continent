package com.example.continent.application.security2;

import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Triển khai tùy chỉnh của PermissionEvaluator để xử lý logic phân quyền ở cấp độ đối tượng (row-level security).
 * Được sử dụng với các annotation như @PreAuthorize("hasPermission(#id, 'continent', 'read')").
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserRepository userRepository;

    /**
     * Được gọi bởi Spring Security để kiểm tra quyền trên một đối tượng cụ thể.
     *
     * @param authentication      Đối tượng Authentication của người dùng đang thực hiện request.
     * @param targetDomainObject  Đối tượng nghiệp vụ cần kiểm tra quyền (ví dụ: một đối tượng Continent).
     * @param permission          Hành động cần kiểm tra (ví dụ: 'READ', 'WRITE', 'DELETE').
     * @return true nếu người dùng có quyền, false nếu không.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }

        // Trong trường hợp này, chúng ta sẽ không triển khai chi tiết cho targetDomainObject
        // mà tập trung vào phương thức có targetId và targetType.
        // Nếu bạn cần kiểm tra quyền trên một đối tượng đã được tải, bạn có thể trích xuất ID và Type từ nó
        // và gọi phương thức hasPermission(authentication, id, type, permission).
        log.warn("hasPermission(Object targetDomainObject) not fully implemented. Returning false.");
        return false;
    }

    /**
     * Được gọi bởi Spring Security để kiểm tra quyền khi chỉ có ID của đối tượng.
     *
     * @param authentication Đối tượng Authentication.
     * @param targetId       ID của đối tượng nghiệp vụ.
     * @param targetType     Loại của đối tượng (ví dụ: 'user', 'project').
     * @param permission     Hành động cần kiểm tra (ví dụ: 'read', 'update', 'delete').
     * @return true nếu có quyền, false nếu không.
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetId == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }

        // 1. Lấy CustomUserDetails từ Authentication
        if (!(authentication.getPrincipal() instanceof CustomUserDetails principal)) {
            log.warn("Principal is not CustomUserDetails. Cannot perform permission check.");
            return false;
        }
        User currentUser = principal.getUser(); // Lấy entity User đang đăng nhập

        // 2. Kiểm tra các trường hợp đặc biệt (ví dụ: admin có mọi quyền)
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            log.debug("User {} is ADMIN, granting permission for {} on {} with ID {}", currentUser.getUsername(), permission, targetType, targetId);
            return true;
        }

        // 3. Phân luồng logic dựa trên loại tài nguyên (targetType)
        String perm = (String) permission; // Cast permission to String
        switch (targetType.toLowerCase()) {
            case "user":
                // Logic cho việc truy cập tài nguyên 'user'
                if ("read".equals(perm)) {
                    // Mọi người dùng đã xác thực đều có thể đọc thông tin user khác?
                    // Hoặc chỉ cho phép đọc thông tin của chính mình hoặc admin
                    log.debug("Checking 'read' permission for user {} on user ID {}", currentUser.getUsername(), targetId);
                    return currentUser.getId().equals(targetId) || authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER_READER"));
                }
                if ("update".equals(perm) || "delete".equals(perm)) {
                    // Chỉ cho phép user tự cập nhật/xóa thông tin của chính mình
                    log.debug("Checking '{}' permission for user {} on user ID {}", perm, currentUser.getUsername(), targetId);
                    return currentUser.getId().equals(targetId);
                }
                break;
            case "continent":
                // Ví dụ: Kiểm tra quyền trên một Continent
                // Bạn sẽ cần inject ContinentRepository và tìm Continent theo ID
                // Sau đó kiểm tra các thuộc tính của Continent hoặc mối quan hệ với currentUser
                log.warn("Permission logic for 'continent' not implemented. Returning false.");
                return false;
            // Thêm các case khác cho các loại tài nguyên khác (e.g., "project", "country")
            default:
                log.warn("Unknown targetType '{}'. Returning false.", targetType);
                return false;
        }

        return false; // Mặc định từ chối nếu không có logic cụ thể nào khớp
    }
}

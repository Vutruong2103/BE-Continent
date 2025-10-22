package com.example.continent.application.security2;

import com.example.continent.application.domain.model.User;
import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * RoleContextHolder sử dụng ThreadLocal để lưu trữ thông tin người dùng (CustomUserDetails)
 * cho request hiện tại. Điều này giúp các tầng nghiệp vụ dễ dàng truy cập thông tin người dùng
 * mà không cần phụ thuộc trực tiếp vào Spring SecurityContextHolder.
 */
@UtilityClass
public class RoleContextHolder {

    private static final ThreadLocal<CustomUserDetails> CONTEXT = new ThreadLocal<>();

    /**
     * Đặt CustomUserDetails vào ThreadLocal cho request hiện tại.
     *
     * @param userDetails Đối tượng CustomUserDetails của người dùng đang đăng nhập.
     */
    public static void setContext(CustomUserDetails userDetails) {
        CONTEXT.set(userDetails);
    }

    /**
     * Lấy CustomUserDetails từ ThreadLocal.
     *
     * @return Optional chứa CustomUserDetails nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<CustomUserDetails> getContext() {
        return Optional.ofNullable(CONTEXT.get());
    }

    /**
     * Lấy entity User đầy đủ từ CustomUserDetails.
     *
     * @return Optional chứa entity User nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<User> getCurrentUser() {
        return getContext().map(CustomUserDetails::getUser);
    }

    /**
     * Lấy ID của người dùng hiện tại.
     *
     * @return Optional chứa ID người dùng nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<Long> getCurrentUserId() {
        return getCurrentUser().map(User::getId);
    }

    /**
     * Lấy username của người dùng hiện tại.
     *
     * @return Optional chứa username nếu có, ngược lại là Optional rỗng.
     */
    public static Optional<String> getCurrentUsername() {
        return getCurrentUser().map(User::getUsername);
    }

    /**
     * Xóa ThreadLocal sau khi request kết thúc để tránh rò rỉ bộ nhớ và dữ liệu.
     */
    public static void clearContext() {
        CONTEXT.remove();
    }
}

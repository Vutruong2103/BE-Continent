package com.example.continent.application.validation_;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 
 * @StrongPassword là nhãn dán gắn vào field/parameter để ép buộc password phải mạnh.
 * @Documented: để annotation này được hiển thị trong javadoc
 * @Constraint: nói cho Spring/Been Validation biết rằng annotation này sẽ được validate bởi StrongPasswordValidator
 * @Target: annotation này chỉ dùng được trên field hoặc parameter
 * @Retention: annotation sẽ được giữ lại khi chạy (runtime)
 * message(): thông báo lỗi khi validation thất bại (có thể override)
 * groups() và payload(): là cấu hình chuẩn của Hibernate Validator (để nhóm rule hoặc metadata).
 */
@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Mật khẩu phải có ít nhất 8 ký tự, bao gồm 1 chữ hoa, 1 chữ số và 1 ký tự đặc biệt";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
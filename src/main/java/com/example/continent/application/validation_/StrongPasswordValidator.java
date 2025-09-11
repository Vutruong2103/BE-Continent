package com.example.continent.application.validation_;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * PASSWORD_PATTERN: Regex rule cho password:
 * (?=.*[A-Z]) → phải có ít nhất 1 chữ hoa.
 * (?=.*[0-9]) → phải có ít nhất 1 chữ số.
 * (?=.*[@#$%^&+=!]) → phải có ít nhất 1 ký tự đặc biệt.
 * .{8,}$ → phải dài tối thiểu 8 ký tự.
 */
public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // password không được null
        }
        return value.matches(PASSWORD_PATTERN);
    }
}


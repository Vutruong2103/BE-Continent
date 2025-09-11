package com.example.continent.application.request;

import lombok.Data;

/**
 * LoginDto: Lớp này dùng để nhận dữ liệu đăng nhập từ client, bao gồm tên người dùng và mật khẩu.
 */
@Data
public class LoginDto {
    private String username;
    private String password;
}

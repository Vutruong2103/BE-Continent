package com.example.continent.application.request;

import lombok.Builder;
import lombok.Data;

/**
 * ApiResponse: Lớp này dùng để chuẩn hóa phản hồi từ API, bao gồm mã trạng thái, thông điệp và dữ liệu trả về.
 *
 * @param <T>
 */
@Data
@Builder
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
}
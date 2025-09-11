package com.example.continent.application.dto_;

import lombok.Data;

@Data
public class ContinentDto {
    private Long Id; // <- Lỗi nghiêm trọng :  Chữ I viết hoa là sai chuẩn Java. Sửa lại thành id
    private String code;
    private String name;
}

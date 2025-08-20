package com.example.continent.application.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(makeFinal = true)
public class UserDto {
     Long id;
     String username;
     String password; //ẩn trong response
    Set<String> roleName; // Chỉ lấy tên của các role, không lấy id
    List<Long> roleId;
//    private List<RoleDto> roles;
}
    
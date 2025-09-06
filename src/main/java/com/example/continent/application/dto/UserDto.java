package com.example.continent.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private  Long id;
    private  String username;
    private String password; //ẩn trong response
    private List<String> roleName;
    private List<Long> roleIds;
//    private List<RoleDto> roles;
}


    
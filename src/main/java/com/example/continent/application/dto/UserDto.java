package com.example.continent.application.dto;

import com.example.continent.application.validation.StrongPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class UserDto {
    private Long id;

    @Size(min = 3, message = "Username must be at least 3 characters")
    private String username;

    @StrongPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<String> roleName;
    private List<Long> roleIds;
}


    
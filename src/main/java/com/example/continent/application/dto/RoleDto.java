package com.example.continent.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private List<Long> userIds;
}

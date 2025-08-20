package com.example.continent.application.mapper;


import com.example.continent.application.dto.RoleDto;
import com.example.continent.application.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto dto);
}
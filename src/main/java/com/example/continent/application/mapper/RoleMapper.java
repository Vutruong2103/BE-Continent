package com.example.continent.application.mapper;


import com.example.continent.application.dto.RoleDto;
import com.example.continent.application.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "role.id")
    @Mapping(target = "name", source = "role.name")
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);
}
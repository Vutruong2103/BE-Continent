package com.example.continent.application.mapper;


import com.example.continent.application.dto.RoleDto;
import com.example.continent.application.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

//@Mapper(componentModel = "spring")
//public interface RoleMapper {
//    RoleDto toDto(Role role);
//    Role toEntity(RoleDto dto);
@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "role.id")
    @Mapping(target = "name", source = "role.name")
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    @Mapping(target = "id", ignore = true) // Giữ nguyên id hiện tại
    void updateRoleFromDto(RoleDto roleDto, @MappingTarget Role role);
}
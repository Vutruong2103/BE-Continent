package com.example.continent.application.mapper;


import com.example.continent.application.domain.model.User;
import com.example.continent.application.dto.RoleDto;
import com.example.continent.application.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "userIds", source = "role", qualifiedByName = "mapUserIds")
    RoleDto toDto(Role role);

    @Named("mapUserIds")
    default List<Long> getUserIds(Role role) {
        if (role == null || role.getUsers() == null) return List.of();
        return role.getUsers().stream().map(User::getId).toList();
    }

    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RoleDto dto, @MappingTarget Role role);
}

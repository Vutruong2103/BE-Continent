package com.example.continent.application.mapper_;

import com.example.continent.application.dto_.UserDto;
import com.example.continent.domain.model_.Role;
import com.example.continent.domain.model_.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

/**
 * @TODO : Cái ni em cần chú ý logic.
 * 
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleName", source = "user", qualifiedByName = "mapRoleNames")
    @Mapping(target = "roleIds", source = "user", qualifiedByName = "mapRoleIds")
    UserDto toDTO(User user);

    @Named("mapRoleNames")
    default List<String> getRoleNames(User user) {
        if (user == null || user.getRoles() == null)
            return List.of();
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    @Named("mapRoleIds")
    default List<Long> getRoleIds(User user) {
        if (user == null || user.getRoles() == null)
            return List.of();
        return user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
    }

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserDto userDto);

    @Named("mapRoleIdsToRoles")
    default List<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        if (roleIds == null)
            return List.of();
        return roleIds.stream().map(id -> {
            Role role = new Role();
            role.setId(id);
            return role;
        }).collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user); // <- Cái ni là để update user từ userDto, nhưng các trường lastModifiedBy và lastModifiedDate thì sao ? Thiếu rồi. Có thể sai logic 


    @AfterMapping
    default void afterUpdateUserFromDto(UserDto userDto, @MappingTarget User user) {
        if (userDto.getUsername() == null) {
            user.setUsername(user.getUsername());
        }
    }
}


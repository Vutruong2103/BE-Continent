package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Role;
import com.example.continent.application.dto.UserDto;
import com.example.continent.application.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




import java.util.List;

import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;



@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity -> DTO
    @Mapping(target = "roleName", source = "user", qualifiedByName = "mapRoleNames")
    @Mapping(target = "roleIds", source = "user", qualifiedByName = "mapRoleIds")
    UserDto toDTO(User user);

    // Helpers: Entity -> DTO
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

    // DTO -> Entity
    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserDto userDto);

    // Helpers: DTO -> Entity
    @Named("mapRoleIdsToRoles")
    default List<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        if (roleIds == null)
            return List.of();
        return roleIds.stream().map(id -> {
            Role role = new Role();
            role.setId(id); // chỉ set id, không cần load full entity
            return role;
        }).collect(Collectors.toList());
    }

    // Update User từ UserDto (không override id)
    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);

    @AfterMapping
    default void afterUpdateUserFromDto(UserDto userDto, @MappingTarget User user) {
        // Ví dụ: Nếu một trường trong DTO null thì giữ giá trị cũ
        if (userDto.getUsername() == null) {
            // giữ nguyên username hiện tại
        }
//        if (userDto.getEmail() == null) {
//            // giữ nguyên email hiện tại
//        }
        // Có thể bổ sung logic thêm nếu cần
    }
}

//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    @Mapping(target = "roleId", source = "roles", qualifiedByName = "mappingRuleRole")
//    @Mapping(target = "roleName", source = "roles", qualifiedByName = "mappingRuleRoleName")
//    UserDto toDto(User user);
//
//    @Mapping(target = "roles", ignore = true)
//    User toEntity(UserDto dto);
//
//    @Named("mappingRuleRole")
//    default List<Long> mappingRuleRole(List<Role> roles) {
//        if (roles == null || roles.isEmpty()) return List.of();
////        if (roles == null) return null;
//        return roles.stream()
//                .map(Role::getId)   // lấy id
//                .collect(Collectors.toList());
//    }
//
//    @Named("mappingRuleRoleName")
//    default Set<String> mappingRuleRoleName(List<Role> roles) {
//        if (roles == null || roles.isEmpty()) return Set.of();
//        //if (roles == null) return null;
//        List<String> listRoleName = roles.stream()
//                .map(Role::getName)   // lấy id
//                .toList();
//        Set<String> result = Set.copyOf(listRoleName);
//        return result;
//    }


//    client -> userRestUpdate (username, password, email);
//    userRestUpdate gửi thêm id.
//    Service Optional<entity> user = findEntityById(id)
//    thông tin user .setUserName(userRestUpdate.getUsername())
//            ..... 1000 fields
//    userRepository.save(user);
//
//    thay vào đó mapper có 1 cách thực hiện mapping trường của userRestUpdate với user
//    sao đó lưu :
//      userRepository.save(user);
//}


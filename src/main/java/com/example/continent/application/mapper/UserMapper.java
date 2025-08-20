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


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleId", source = "roles", qualifiedByName = "mappingRuleRole")
    @Mapping(target = "roleName", source = "roles", qualifiedByName = "mappingRuleRoleName")
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto dto);

    @Named("mappingRuleRole")
    default List<Long> mappingRuleRole(List<Role> roles) {
        if (roles == null || roles.isEmpty()) return List.of();
//        if (roles == null) return null;
        return roles.stream()
                .map(Role::getId)   // lấy id
                .collect(Collectors.toList());
    }

    @Named("mappingRuleRoleName")
    default Set<String> mappingRuleRoleName(List<Role> roles) {
        if (roles == null || roles.isEmpty()) return Set.of();
        //if (roles == null) return null;
        List<String> listRoleName = roles.stream()
                .map(Role::getName)   // lấy id
                .toList();
        Set<String> result = Set.copyOf(listRoleName);
        return result;
    }


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
}


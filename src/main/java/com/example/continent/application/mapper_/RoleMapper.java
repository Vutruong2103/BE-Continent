package com.example.continent.application.mapper_;


import com.example.continent.application.dto_.RoleDto;
import com.example.continent.domain.model_.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

    /**
     * @TODO : Tạo không có update cho Role hã em chai ? Lỡ họ thêm mới cái role rồi viết sai tên không cho họ sửa hã ?
     */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "role.id")
    @Mapping(target = "name", source = "role.name")
    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto); // Vì sao không có mapping user ở đây ? Nếu không có phải đánh ignore chứ nhỉ ? 

}
package com.example.continent.application.domain.service;

import com.example.continent.application.dto.RoleDto;
import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Long id, RoleDto dto);
    void delete(Long id);
    RoleDto getById(Long id);
    List<RoleDto> getAll();
}

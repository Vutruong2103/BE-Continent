package com.example.continent.application.domain.service;

import com.example.continent.application.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Long id, RoleDto dto);
    void delete(Long id);
    RoleDto getById(Long id);
    Page<RoleDto> getAll(Pageable pageable);
}

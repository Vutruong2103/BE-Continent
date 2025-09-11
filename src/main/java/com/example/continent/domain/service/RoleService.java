package com.example.continent.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.continent.application.dto_.RoleDto;
import com.example.continent.domain.model_.Role;


public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Long id, RoleDto dto);
    void delete(Long id);
    RoleDto getById(Long id);
    Page<RoleDto> getAll(Pageable pageable);

    // write a method as an example
    Role getRoleByName(String name);
}

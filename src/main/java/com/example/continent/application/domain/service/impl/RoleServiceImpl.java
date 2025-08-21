package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Role;
import com.example.continent.application.domain.repository.RoleRepository;
import com.example.continent.application.domain.service.RoleService;
import com.example.continent.application.dto.RoleDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleDto create(RoleDto dto) {
        // Kiểm tra trùng tên role
        Optional<Role> existing = roleRepository.findByName(dto.getName());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("Role with name '" + dto.getName() + "' already exists");
        }

        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleDto update(Long id, RoleDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));

        // Kiểm tra trùng tên role (nếu cập nhật tên)
        roleRepository.findByName(dto.getName())
                .filter(r -> !r.getId().equals(id))
                .ifPresent(r -> {
                    throw new DuplicateResourceException("Role with name '" + dto.getName() + "' already exists");
                });

        role.setName(dto.getName());
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role with id " + id + " not found");
        }
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }
}

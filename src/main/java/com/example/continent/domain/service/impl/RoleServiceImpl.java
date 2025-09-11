package com.example.continent.domain.service.impl;

import com.example.continent.application.dto_.RoleDto;
import com.example.continent.application.exception_.DuplicateResourceException;
import com.example.continent.application.exception_.ResourceNotFoundException;
import com.example.continent.application.mapper_.RoleMapper;
import com.example.continent.domain.model_.Role;
import com.example.continent.domain.repository_.RoleRepository;
import com.example.continent.domain.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public RoleDto create(RoleDto dto) {
        roleRepository.findByNameAndDeletedFalse(dto.getName())
                .ifPresent(r -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.role.exists",
                                    new Object[]{dto.getName()}, LocaleContextHolder.getLocale())
                    );
                });

        Role role = roleMapper.toEntity(dto);
        role.setDeleted(false);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleDto update(Long id, RoleDto dto) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.role.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        roleRepository.findByNameAndDeletedFalse(dto.getName())
                .filter(r -> !r.getId().equals(id))
                .ifPresent(r -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.role.exists",
                                    new Object[]{dto.getName()}, LocaleContextHolder.getLocale())
                    );
                });
        role.setName(dto.getName());
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Role role = roleRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.role.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        role.setDeleted(true);
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getById(Long id) {
        return roleRepository.findByIdAndDeletedFalse(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.role.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDto> getAll(Pageable pageable) {
        return roleRepository.findAllByDeletedFalse(pageable)
                .map(roleMapper::toDto);
    }

    @Override
    public Role getRoleByName(String name) {
       return roleRepository.findByNameAndDeletedFalse(name).orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.role.notfound",
                                new Object[]{name}, LocaleContextHolder.getLocale())));
    }
}

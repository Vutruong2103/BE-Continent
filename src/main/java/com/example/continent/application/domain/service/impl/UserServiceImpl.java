package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Role;
import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.RoleRepository;
import com.example.continent.application.domain.repository.UserRepository;
import com.example.continent.application.domain.service.UserService;
import com.example.continent.application.dto.UserDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        userRepository.findByUsernameAndDeletedFalse(dto.getUsername())
                .ifPresent(u -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.user.exists",
                                    new Object[]{dto.getUsername()}, LocaleContextHolder.getLocale())
                    );
                });

        User user = userMapper.toEntity(dto);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        applyRoles(dto, user);

        user.setDeleted(false);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        userRepository.findByUsernameAndDeletedFalse(dto.getUsername())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.user.exists",
                                    new Object[]{dto.getUsername()}, LocaleContextHolder.getLocale())
                    );
                });

        userMapper.updateUserFromDto(dto, user);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userMapper.toDTO(userRepository.save(user));
    }


    private void applyRoles(UserDto dto, User user) {
        if (dto.getRoleIds() != null) {
            if (dto.getRoleIds().isEmpty()) {
                user.setRoles(List.of());
            } else {
                List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
                if (roles.isEmpty()) {
                    throw new ResourceNotFoundException(
                            messageSource.getMessage("error.role.notfound", null, LocaleContextHolder.getLocale())
                    );
                }
                user.setRoles(roles);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findByIdAndDeletedFalse(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> searchByName(String keyword) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users;

        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findAllByDeletedFalse(pageable);
        } else {
            users = userRepository.findByUsernameContainingIgnoreCaseAndDeletedFalse(keyword, pageable);
        }

        return users.map(userMapper::toDTO);
    }


    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{username}, LocaleContextHolder.getLocale())
                ));
    }
}

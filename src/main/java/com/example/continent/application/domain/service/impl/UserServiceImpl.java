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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        // Kiểm tra trùng username
        Optional<User> existing = userRepository.findByUsername(dto.getUsername());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("User with username '" + dto.getUsername() + "' already exists");
        }

        User user = userMapper.toEntity(dto);
        if (dto.getRoleId() != null && !dto.getRoleId().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        // Kiểm tra trùng username (trừ chính nó)
        userRepository.findByUsername(dto.getUsername())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> {
                    throw new DuplicateResourceException("User with username '" + dto.getUsername() + "' already exists");
                });

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        if (dto.getRoleId() != null && !dto.getRoleId().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println("User: " + u.getUsername() + " - roles: " + u.getRoles()));
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}

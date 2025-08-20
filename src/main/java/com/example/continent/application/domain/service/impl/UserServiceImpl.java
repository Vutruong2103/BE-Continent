package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Role;
import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.RoleRepository;
import com.example.continent.application.domain.repository.UserRepository;
import com.example.continent.application.domain.service.UserService;
import com.example.continent.application.dto.UserDto;
import com.example.continent.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserDto create(UserDto dto) {
        User user = userMapper.toEntity(dto);
        if (dto.getRoleId() != null && !dto.getRoleId().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        if (dto.getRoleId() != null) {
            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
            user.setRoles(roles);
        }
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    // Transactional giữ session mở cho đến khi mapping xong, giúp Hibernate kịp lazy load các collection như roles
    @Transactional(readOnly = true)//readOnly = true chỉ đọc dữ liệu, không update dữ liệu
    public List<UserDto> getAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toDto)
//                .collect(Collectors.toList());
        List<User> users = userRepository.findAll();
        users.forEach(u -> System.out.println("User: " + u.getUsername() + " - roles: " + u.getRoles()));
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}


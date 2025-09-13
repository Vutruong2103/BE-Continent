package com.example.continent.application.domain.service;

import com.example.continent.application.domain.model.User;
import com.example.continent.application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserDto create(UserDto dto);

    UserDto update(Long id, UserDto dto);

    void delete(Long id);

    UserDto getById(Long id);

    Page<UserDto> getAll(Pageable pageable);

    Page<UserDto> searchByName(String keyword);

    User getByUsername(String username);
}

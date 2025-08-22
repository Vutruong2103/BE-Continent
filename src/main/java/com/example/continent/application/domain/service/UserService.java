package com.example.continent.application.domain.service;

import com.example.continent.application.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto update(Long id, UserDto dto);
    void delete(Long id);
    UserDto getById(Long id);
//    List<UserDto> getAll();
    Page<UserDto> getAll(Pageable pageable);
}

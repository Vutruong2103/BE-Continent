package com.example.continent.domain.service;

import com.example.continent.application.dto_.ContinentDto; // dư
import com.example.continent.application.dto_.UserDto;
import com.example.continent.domain.model_.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional; // dư

public interface UserService {
    UserDto create(UserDto dto);
    UserDto update(Long id, UserDto dto);
    void delete(Long id);
    UserDto getById(Long id);
    Page<UserDto> getAll(Pageable pageable);
    List<UserDto> searchByName(String keyword);
    User getByUsername(String username);

    // write a method as an example
    User save(User user);
}

package com.example.continent.application.domain.service;

import com.example.continent.application.request.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}

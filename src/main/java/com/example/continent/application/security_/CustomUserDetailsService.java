package com.example.continent.application.security_;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.continent.domain.model_.User;
import com.example.continent.domain.repository_.UserRepository;


/**
 * implements UserDetailsService Khi login, Spring Security sẽ gọi loadUserByUsername(username) để lấy thông tin user từ DB.
 * Chính vì vậy cần tự viết CustomUserDetailsService để Spring biết cách tìm user của mình
 * loadUserByUsername tìm username trong db, trả về UserDetails đã được UserDetailsCustom
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new UserDetailsCustom(u);
    }
}

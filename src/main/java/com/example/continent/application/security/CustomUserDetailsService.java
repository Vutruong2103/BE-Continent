package com.example.continent.application.security;

import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


/**
 * CustomUserDetailsService lấy user chưa xác thực (chưa login thành công) để check login,nếu đúng thì đem đi tạo jwt
 * implements UserDetailsService Khi login, Spring Security sẽ gọi loadUserByUsername(username) để lấy thông tin user từ DB.
 * UserDetailsService: (user security của spring) Lớp chứa cac thong tin cua user đã login thành công
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

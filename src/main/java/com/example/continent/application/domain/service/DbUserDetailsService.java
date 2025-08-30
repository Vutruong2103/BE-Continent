//package com.example.continent.application.domain.service;
//
//import com.example.continent.application.domain.model.User;
//import com.example.continent.application.domain.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class DbUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Lấy user từ DB (chỉ lấy user chưa bị xóa)
//        User u = userRepository.findByUsernameAndDeletedFalse(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Chuyển roles -> GrantedAuthority
//        List<GrantedAuthority> authorities = u.getRoles().stream()
//                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase()))
//                .collect(Collectors.toList()); // an toàn trên JDK 8+
//
//        // Trả về UserDetails cho Spring Security
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(u.getUsername())
//                .password(u.getPassword())
//                .authorities(authorities)
//                .accountLocked(false)
//                .disabled(false)
//                .build();
//    }
//}
//

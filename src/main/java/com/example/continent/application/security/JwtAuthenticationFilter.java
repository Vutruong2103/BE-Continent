//package com.example.continent.application.security;
//
//import com.example.continent.application.domain.service.DbUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final DbUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        //lấy token từ header
//        String header = request.getHeader("Authorization");
//        String token = null, username = null;
//
//        if (header != null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//            if (jwtUtil.validate(token)) {
//                username = jwtUtil.extractUsername(token);
//            }
//        }
//
//        //xác thực user và set vào context
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails details = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
//            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//        chain.doFilter(request, response);
//    }
//}
//

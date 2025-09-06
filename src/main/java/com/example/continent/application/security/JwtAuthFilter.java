package com.example.continent.application.security;

import com.example.continent.application.domain.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtTokenProvider;
//    private final CustomUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String path = request.getServletPath();
//        if (path.startsWith("/test")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String token = getTokenFromRequest(request);
//        // validate token
//        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
//            // get username from token
//            String username = jwtTokenProvider.getUsername(token);
//            // load the user associated with token
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        return null;
//    }
//}


//    private final JwtService jwtService;
//    private final CustomUserDetailsService uds;
//
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain) throws ServletException, IOException {
//        // Bỏ qua các endpoint auth
////        String path = req.getServletPath();
////        if (path.startsWith("/api/auth/login")) {
////            chain.doFilter(req, res);
////            return;
////        }
//
//        final String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            chain.doFilter(req, res);
//            return;
//        }
//        String token = authHeader.substring(7);
//        String username;
//        try {
//            username = jwtService.extractUsername(token);
//        } catch (Exception e) {
//            chain.doFilter(req, res);
//            return;
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails user = uds.loadUserByUsername(username);
//            if (jwtService.isTokenValid(token, user.getUsername())) {
//                UsernamePasswordAuthenticationToken auth =
//                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//        chain.doFilter(req, res);
//    }


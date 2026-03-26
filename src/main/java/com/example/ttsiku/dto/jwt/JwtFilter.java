package com.example.ttsiku.dto.jwt;

import com.example.ttsiku.entity.User;
import com.example.ttsiku.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.info("Authorization header: {}", authHeader);

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                String username = jwtUtil.getUsername(token);

                User user = userRepository.findByUsernameWithRoles(username).orElse(null);

                if (user != null) {
                    var authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                            .toList();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
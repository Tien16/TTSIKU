package com.example.ttsiku.controller;

import com.example.ttsiku.dto.LoginDto;
import com.example.ttsiku.dto.RegisterDto;
import com.example.ttsiku.dto.jwt.JwtUtil;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        User user = userService.findByUsername(dto.getUsername());
        if (user == null) return ResponseEntity.status(401).body("Username not found");

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            return ResponseEntity.status(401).body("Invalid password");

        String token = jwtUtil.generateToken(user);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);
        res.put("username", user.getUsername());
        res.put("roles", user.getRoles().stream().map(r -> r.getRoleName()).toList());

        return ResponseEntity.ok(res);
    }
}
package com.example.ttsiku.service;

import com.example.ttsiku.dto.RegisterDto;
import com.example.ttsiku.entity.Role;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.repository.RoleRepository;
import com.example.ttsiku.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByUserId(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
    //Them
    public User create(User user) {
        return userRepository.save(user);
    }
    //Sua
    public User update(Integer id, User newUser) {
        User user = getByUserId(id);
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setStatus(newUser.getStatus());
        return userRepository.save(user);
    }
    //Xoa
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void register(RegisterDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }

        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
    }
}

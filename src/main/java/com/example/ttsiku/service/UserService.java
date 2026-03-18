package com.example.ttsiku.service;

import com.example.ttsiku.entity.User;
import com.example.ttsiku.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        user.setRole(newUser.getRole());
        user.setStatus(newUser.getStatus());
        return userRepository.save(user);
    }
    //Xoa
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}

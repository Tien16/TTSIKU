package com.example.ttsiku.controller;

import com.example.ttsiku.entity.User;
import com.example.ttsiku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Integer userId) {
        return userService.getByUserId(userId);
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable Integer userId, @RequestBody User user) {
        return userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Integer userId) {
        userService.delete(userId);
        return "Deleted successfully";
    }
}

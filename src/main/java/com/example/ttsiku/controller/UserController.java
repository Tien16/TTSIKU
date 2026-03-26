package com.example.ttsiku.controller;

import com.example.ttsiku.entity.User;
import com.example.ttsiku.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Quản lý người dùng")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lấy danh sách người dùng", description = "Trả về toàn bộ người dùng")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Lấy người dùng theo ID")
    public User getById(
            @Parameter(description = "ID của người dùng", example = "1")
            @PathVariable Integer userId
    ) {
        return userService.getByUserId(userId);
    }

//    @PutMapping("/{userId}")
//    @Operation(summary = "Cập nhật người dùng")
//    public User update(@PathVariable Integer userId, @RequestBody User user) {
//        return userService.update(userId, user);
//    }

}
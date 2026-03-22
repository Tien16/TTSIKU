package com.example.ttsiku.controller;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.PutStatusDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.exception.BadRequestException;
import com.example.ttsiku.repository.UserRepository;
import com.example.ttsiku.response.ApiResponse;
import com.example.ttsiku.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

//    // CREATE
//    @PostMapping
//    public TaskDTO create(@RequestBody PostTaskDto request) {
//        return taskService.create(request);
//    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<?> getMyTasks(Authentication authentication) {

        String username = authentication.getName();

        return ApiResponse.success(taskService.getMyTasks(username));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<?> getByUser(@PathVariable Integer userId,
                                    Authentication authentication) {

        String username = authentication.getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow();

        if (!currentUser.getUserId().equals(userId)) {
            throw new BadRequestException("Không có quyền xem task của người khác");
        }

        return ApiResponse.success(taskService.getByUser(userId));
    }

    // GET ALL
    @GetMapping
    public List<TaskDTO> getAll() {
        return taskService.getAll();
    }

//    // GET BY USER
//    @GetMapping("/user/{userId}")
//    public List<TaskDTO> getByUser( @PathVariable Integer userId) {
//        return taskService.getByUser(userId);
//    }

    // GET BY PROJECT
    @GetMapping("/project/{projectId}")
    public List<TaskDTO> getByProject( @PathVariable Integer projectId) {
        return taskService.getByProject(projectId);
    }

    @PostMapping
    public PostTaskDto postTaskDto(@Valid @RequestBody PostTaskDto request) {
        return taskService.postTaskDto(request);
    }

    //API assign Task cho User
    @PutMapping("/{taskId}/assign/{userId}")
    public TaskDTO assignTask(@Valid
            @PathVariable Integer taskId,
            @PathVariable Integer userId) {

        return taskService.assignTask(taskId, userId);
    }

    @PutMapping("/{taskId}/status")
    public TaskDTO updateStatus(@Valid
            @PathVariable Integer taskId,
            @RequestBody PutStatusDto request) {

        return taskService.putStatus(taskId, request.getStatus());
    }
}
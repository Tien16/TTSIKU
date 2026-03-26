package com.example.ttsiku.controller;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.PutStatusDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.response.ApiResponse;
import com.example.ttsiku.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER','MANAGER')")
    public ApiResponse<?> getMyTasks(Authentication authentication) {
        String username = authentication.getName();
        return ApiResponse.success(taskService.getMyTasks(username));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ApiResponse<?> getByUser(@PathVariable Integer userId) {
        return ApiResponse.success(taskService.getByUser(userId));
    }

    @GetMapping
    public List<TaskDTO> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDTO> getByProject(@PathVariable Integer projectId) {
        return taskService.getByProject(projectId);
    }

    @PostMapping
    public PostTaskDto postTaskDto(@Valid @RequestBody PostTaskDto request) {
        return taskService.postTaskDto(request);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public TaskDTO assignTask(@Valid
                              @PathVariable Integer taskId,
                              @PathVariable Integer userId) {
        return taskService.assignTask(taskId, userId);
    }

    @PutMapping("/{taskId}/status")
    public TaskDTO updateStatus(@PathVariable Integer taskId,
                                @Valid @RequestBody PutStatusDto request) {
        return taskService.putStatus(taskId, request.getStatus());
    }
}
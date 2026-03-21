package com.example.ttsiku.controller;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.PutStatusDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // CREATE
    @PostMapping
    public TaskDTO create(@RequestBody PostTaskDto request) {
        return taskService.create(request);
    }

    // GET ALL
    @GetMapping
    public List<TaskDTO> getAll() {
        return taskService.getAll();
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public List<TaskDTO> getByUser(@PathVariable Integer userId) {
        return taskService.getByUser(userId);
    }

    // GET BY PROJECT
    @GetMapping("/project/{projectId}")
    public List<TaskDTO> getByProject(@PathVariable Integer projectId) {
        return taskService.getByProject(projectId);
    }

    //API assign Task cho User
    @PutMapping("/{taskId}/assign/{userId}")
    public TaskDTO assignTask(
            @PathVariable Integer taskId,
            @PathVariable Integer userId) {

        return taskService.assignTask(taskId, userId);
    }

    @PutMapping("/{taskId}/status")
    public TaskDTO updateStatus(
            @PathVariable Integer taskId,
            @RequestBody PutStatusDto request) {

        return taskService.putStatus(taskId, request.getStatus());
    }
}
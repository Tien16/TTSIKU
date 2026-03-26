package com.example.ttsiku.controller;

import com.example.ttsiku.dto.PostProjectDto;
import com.example.ttsiku.dto.ProjectDTO;
import com.example.ttsiku.response.ApiResponse;
import com.example.ttsiku.service.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    private final ProjectService projectService;

    // CREATE project - chỉ MANAGER
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ApiResponse<?> createProject(@RequestBody PostProjectDto projectDTO) {
        return ApiResponse.success(projectService.create(projectDTO));
    }

    // GET ALL projects
    @GetMapping
    public List<ProjectDTO> getAll() {
        return projectService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProjectDTO getById(@PathVariable Integer id) {
        return projectService.getById(id);
    }

    // DELETE project
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String delete(@PathVariable Integer id) {
        projectService.delete(id);
        return "Deleted successfully";
    }
}
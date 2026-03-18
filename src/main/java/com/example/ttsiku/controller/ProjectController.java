package com.example.ttsiku.controller;

import com.example.ttsiku.dto.PostProjectDto;
import com.example.ttsiku.dto.ProjectDTO;
import com.example.ttsiku.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // CREATE
    @PostMapping
    public ProjectDTO create(@RequestBody PostProjectDto request) {
        return projectService.create(request);
    }

    // GET ALL
    @GetMapping
    public List<ProjectDTO> getAll() {
        return projectService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ProjectDTO getById(@PathVariable Integer id) {
        return projectService.getById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        projectService.delete(id);
        return "Deleted successfully";
    }
}
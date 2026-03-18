package com.example.ttsiku.service;

import com.example.ttsiku.dto.PostProjectDto;
import com.example.ttsiku.dto.ProjectDTO;
import com.example.ttsiku.entity.Project;
import com.example.ttsiku.mapper.ProjectMapper;
import com.example.ttsiku.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    // CREATE
    public ProjectDTO create(PostProjectDto request) {
        Project project = new Project();
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());

        return ProjectMapper.toDTO(projectRepository.save(project));
    }

    // GET ALL
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public ProjectDTO getById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return ProjectMapper.toDTO(project);
    }

    // DELETE
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
}
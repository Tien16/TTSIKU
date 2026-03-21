package com.example.ttsiku.repository;

import com.example.ttsiku.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer> {
    boolean existsByProjectProjectIdAndUserUserId(Integer projectId, Integer userId);
}

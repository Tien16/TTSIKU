package com.example.ttsiku.repository;

import com.example.ttsiku.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserUserId(Integer userId);

    List<Task> findByProjectProjectId(Integer projectId);

}

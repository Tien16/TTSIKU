package com.example.ttsiku.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    private String projectName;
    private String description;
    private String status;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;
}

package com.example.teamuptool.repository;

import com.example.teamuptool.model.Project;
import com.example.teamuptool.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> getAllByProjectStatus(ProjectStatus projectStatus);
    Optional<Project> findByName(String name);
}

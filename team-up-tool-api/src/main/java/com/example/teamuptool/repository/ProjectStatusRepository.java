package com.example.teamuptool.repository;


import com.example.teamuptool.model.ProjectManager;
import com.example.teamuptool.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer > {

    Optional<ProjectStatus> getByName(String name);
}

package com.example.teamuptool.repository;

import com.example.teamuptool.model.Employee;
import com.example.teamuptool.model.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Integer > {

    Optional<ProjectManager> findByAccount_Email(String email);
    List<ProjectManager> findAll();
}

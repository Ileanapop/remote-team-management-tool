package com.example.teamuptool.repository;

import com.example.teamuptool.model.ProjectRole;
import com.example.teamuptool.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends JpaRepository<ProjectRole, Integer > {

}

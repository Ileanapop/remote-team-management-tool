package com.example.teamuptool.repository;

import com.example.teamuptool.model.ProjectManager;
import com.example.teamuptool.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleTypesRepository extends JpaRepository<RoleType, Integer > {

    List<RoleType> getAllByNameIsNotIn(List<String> names);
    Optional<RoleType> findByName(String name);
}

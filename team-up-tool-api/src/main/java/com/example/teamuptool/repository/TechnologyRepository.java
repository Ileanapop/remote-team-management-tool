package com.example.teamuptool.repository;


import com.example.teamuptool.model.RoleType;
import com.example.teamuptool.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TechnologyRepository extends JpaRepository<Technology, Integer > {

    List<Technology> findAll();

    Optional<Technology> findByName(String name);
}

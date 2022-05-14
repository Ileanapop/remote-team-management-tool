package com.example.teamuptool.repository;

import com.example.teamuptool.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AdministratorRepository extends JpaRepository<Administrator, Integer > {

    Optional<Administrator> findByEmail(String email);

}

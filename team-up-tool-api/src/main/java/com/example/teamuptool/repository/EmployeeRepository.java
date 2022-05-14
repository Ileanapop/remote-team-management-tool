package com.example.teamuptool.repository;

import com.example.teamuptool.model.Customer;
import com.example.teamuptool.model.Employee;
import com.example.teamuptool.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EmployeeRepository extends JpaRepository<Employee, Integer > {

    Optional<Employee> findByAccount_Email(String email);

    List<Employee> findAll();

}

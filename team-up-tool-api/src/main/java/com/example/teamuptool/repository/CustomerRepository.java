package com.example.teamuptool.repository;

import com.example.teamuptool.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CustomerRepository extends JpaRepository<Customer, Integer > {

    Optional<Customer> findByAccount_Email(String email);
}

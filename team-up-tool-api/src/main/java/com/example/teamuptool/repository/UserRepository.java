package com.example.teamuptool.repository;

import com.example.teamuptool.model.Account;
import com.example.teamuptool.model.Administrator;
import com.example.teamuptool.model.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<RegularUser, Integer > {

    Optional<RegularUser> findByAccount(Account account);
    Optional<RegularUser> findByAccount_Email(String email);

}

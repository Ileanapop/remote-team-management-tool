package com.example.teamuptool.repository;

import com.example.teamuptool.model.Account;
import com.example.teamuptool.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AccountRepository extends JpaRepository<Account, Integer > {


}

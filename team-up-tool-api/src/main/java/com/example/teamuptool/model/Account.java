package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_account", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "account")
    RegularUser regularUser;

    public Account(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public Account(){

    }

}

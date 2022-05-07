package com.example.teamuptool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "administrators")
public class Administrator {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_administrator", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public Administrator(String password, String email){
        this.password = password;
        this.email = email;
    }

    public Administrator(){

    }
}

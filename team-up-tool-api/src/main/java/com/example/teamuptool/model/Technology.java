package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "technologies")
public class Technology {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_technology", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "technology")
    private List<Employee> employees;
}

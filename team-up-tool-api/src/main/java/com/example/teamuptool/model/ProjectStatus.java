package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "statuses")
public class ProjectStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_status", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "projectStatus")
    private List<Project> projects;

}

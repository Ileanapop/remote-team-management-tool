package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_project", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    /*@OneToOne(mappedBy = "project")
    private ProjectManager projectManager;*/

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProjectStatus projectStatus;


    @OneToMany(mappedBy = "project")
    private List<ProjectEvent> events;

    @OneToMany(mappedBy = "companyProject")
    private List<ProjectRole> roles;

    /*@OneToOne(mappedBy = "project")
    private Customer customer;*/

}

package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "roles")
public class ProjectRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_role", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "projectRole")
    private List<RegularUser> users;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project companyProject;

    @ManyToOne
    @JoinColumn(name = "role_type_id")
    private RoleType roleType;
}

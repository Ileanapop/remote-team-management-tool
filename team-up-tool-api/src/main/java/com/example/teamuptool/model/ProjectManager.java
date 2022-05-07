package com.example.teamuptool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@DiscriminatorValue("2")
public class ProjectManager extends RegularUser {

    @OneToMany(mappedBy = "projectManager")
    private List<Employee> employees;

    /*@OneToOne
    @JoinColumn(name = "project_id")
    private Project project;*/


    @OneToMany(mappedBy = "projectManager")
    private List<Task> tasks;

}

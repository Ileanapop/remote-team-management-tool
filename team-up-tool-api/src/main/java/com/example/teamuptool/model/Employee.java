package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@DiscriminatorValue("1")
public class Employee extends RegularUser{

    @ManyToOne
    @JoinColumn(name = "reports_to")
    private ProjectManager projectManager;


    /*@ManyToOne
    @JoinColumn(name = "role_id")
    private ProjectRole projectRole;*/

    @ManyToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;

    @OneToMany(mappedBy = "employee")
    private List<Task> tasks;

}

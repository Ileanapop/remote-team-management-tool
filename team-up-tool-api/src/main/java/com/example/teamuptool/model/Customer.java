package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@DiscriminatorValue("3")
public class Customer extends RegularUser{

    @Column(name = "company")
    private String company;

    /*@OneToOne
    @JoinColumn(name = "project_id")
    private Project project;*/


}

package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_task", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private ProjectManager projectManager;
}

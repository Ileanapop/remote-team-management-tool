package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "events")
public class ProjectEvent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_administrator", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}

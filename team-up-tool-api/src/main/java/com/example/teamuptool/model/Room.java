package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_room", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "room")
    private List<Meeting> meetings;

}

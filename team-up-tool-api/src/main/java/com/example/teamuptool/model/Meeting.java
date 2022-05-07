package com.example.teamuptool.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_meeting", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "startTime")
    private Date startTime;

    @Column(name = "endTime")
    private Date endTime;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToMany(mappedBy = "meetings")
    private List<RegularUser> users;

}

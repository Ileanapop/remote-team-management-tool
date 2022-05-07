package com.example.teamuptool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "users")
public class RegularUser {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_user", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_meetings", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id"))
    private List<Meeting> meetings;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ProjectRole projectRole;

}

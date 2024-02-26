package com.schoolcompetition.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "School")
public class School {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

//    @OneToMany(mappedBy = "school")
//    private List<Student> students;
//
//    @OneToMany(mappedBy = "school")
//    private List<Coach> coaches;
//
//    @OneToMany(mappedBy = "school")
//    private List<Team> teams;

}

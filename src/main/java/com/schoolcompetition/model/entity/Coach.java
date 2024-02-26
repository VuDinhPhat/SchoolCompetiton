package com.schoolcompetition.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Coach")
public class Coach {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "sex")
    private char sex;

//    @ManyToOne
//    @JoinColumn(name = "school_id")
//    private School school;
//
//    @OneToMany(mappedBy = "coach")
//    private List<Team> teams;
//
//    @OneToMany(mappedBy = "coach")
//    private List<Contestant> contestants;
}

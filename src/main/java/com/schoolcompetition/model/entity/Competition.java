package com.schoolcompetition.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolcompetition.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "hold_place")
    private String holdPlace;

    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private List<Team> teams;

    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private List<Round> rounds;

    @ManyToOne
    @JoinColumn(name = "school_year_id")
    private SchoolYear schoolYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

}

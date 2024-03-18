package com.schoolcompetition.model.entity;

import com.schoolcompetition.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Car")
public class Car {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(mappedBy = "car")
    private Result result;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

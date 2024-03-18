package com.schoolcompetition.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolcompetition.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Bracket")
public class Bracket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    @OneToMany(mappedBy = "bracket")
    @JsonIgnore
    private List<Match> matches;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

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
@Table(name = "Round")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "map")
    private String map;

    @OneToMany(mappedBy = "round")
    @JsonIgnore
    private List<Bracket> brackets;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

package com.schoolcompetition.model.entity;

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
    private List<Bracket> brackets;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
}

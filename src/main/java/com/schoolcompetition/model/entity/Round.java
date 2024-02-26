package com.schoolcompetition.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Round")
public class Round {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "competition_id")
    private int competitionId;

    @Column(name = "map")
    private String map;
}

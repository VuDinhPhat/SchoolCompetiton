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
@Table(name = "Bracket")
public class Bracket {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "roundId")
    private int round_id;
}

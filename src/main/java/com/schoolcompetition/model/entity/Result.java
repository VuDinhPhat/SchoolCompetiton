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
@Table(name = "Result")
public class Result {
    @Id
    private int id;

    @Column(name = "contestant_id")
    private int contestantId;

    @Column(name = "match_id")
    private int matchId;

    @Column(name = "score")
    private int score;

    @Column(name = "finish_time")
    private String finishTime;

    @Column(name = "car_id")
    private int carId;
}

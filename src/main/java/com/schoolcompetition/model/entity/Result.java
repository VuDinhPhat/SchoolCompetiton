package com.schoolcompetition.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "contestant_id")
//    private int contestantId;

//    @Column(name = "match_id")
//    private int matchId;

    @Column(name = "score")
    private int score;

    @Column(name = "finish_time")
    private String finishTime;

//    @Column(name = "car_id")
//    private int carId;

    @ManyToOne
    @JoinColumn(name = "contestant_id")
    private Contestant contestant;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
}

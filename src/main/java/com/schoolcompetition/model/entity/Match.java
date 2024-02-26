package com.schoolcompetition.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Matchhhhh")
public class Match {
    @Id
    private int id;

    @Column(name = "bracket_id")
    private int bracketId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "place")
    private String place;

    @Column(name = "lap")
    private int lap;



}

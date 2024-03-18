package com.schoolcompetition.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolcompetition.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Matchh")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "place")
    private String place;

    @Column(name = "lap")
    private int lap;

    @ManyToOne
    @JoinColumn(name = "bracket_id")
    private Bracket bracket;

    @OneToMany(mappedBy = "match")
    @JsonIgnore
    private List<Result> results;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

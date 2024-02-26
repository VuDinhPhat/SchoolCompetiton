package com.schoolcompetition.model.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Contestant")
public class Contestant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "student_id")
//    private Student student;

//    @ManyToOne
//    @JoinColumn(name = "coach_id")
//    private Coach coach;
//
//    @ManyToOne
//    @JoinColumn(name = "team_id")
//    private Team team;
//
//    @OneToMany(mappedBy = "contestant")
//    private List<Result> results;
}

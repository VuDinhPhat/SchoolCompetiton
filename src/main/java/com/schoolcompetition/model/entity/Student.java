package com.schoolcompetition.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Student")
public class Student {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "sex")
    private char sex;

//    @ManyToOne
//    @JoinColumn(name = "school_id")
//    private School school;
//
//    @OneToMany(mappedBy = "student")
//    private List<Contestant> contestants;
}

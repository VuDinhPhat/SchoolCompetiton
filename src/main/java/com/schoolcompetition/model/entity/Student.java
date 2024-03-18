package com.schoolcompetition.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolcompetition.enums.Gender;
import com.schoolcompetition.enums.Status;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private Date dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Gender sex;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToOne(mappedBy = "student")
    @JsonIgnore
    private Contestant contestant;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

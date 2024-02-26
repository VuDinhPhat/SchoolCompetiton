package com.schoolcompetition.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "School_Year")
public class SchoolYear {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private int year;

//    @OneToMany(mappedBy = "schoolYear")
//    private List<Competition> competitions;
}

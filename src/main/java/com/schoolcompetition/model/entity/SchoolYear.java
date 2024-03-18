package com.schoolcompetition.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolcompetition.enums.Status;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "year")
    private int year;

    @OneToMany(mappedBy = "schoolYear")
    @JsonIgnore
    private List<Competition> competitions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}

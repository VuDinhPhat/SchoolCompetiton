package com.schoolcompetition.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Competition")
public class Competition {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "hold_place")
    private String holdPlace;

//    @ManyToOne
//    @JoinColumn(name = "school_year_id")
//    private SchoolYear schoolYear;
}

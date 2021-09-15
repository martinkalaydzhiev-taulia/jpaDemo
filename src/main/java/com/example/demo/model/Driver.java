package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@With
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private int wins;
    private int poles;
    private int fastestLaps;
    private double points;
    @OneToOne
    private Team currentTeam;
}

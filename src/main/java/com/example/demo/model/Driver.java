package com.example.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
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

    public Driver() {}

    public Driver(long id, String firstName, String lastName, int wins, int poles, int fastestLaps, double points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.poles = poles;
        this.fastestLaps = fastestLaps;
        this.points = points;
    }
}

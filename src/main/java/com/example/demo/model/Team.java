package com.example.demo.model;


import lombok.*;

import javax.persistence.*;

@With
@Entity
@Table(name = "teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;
    private Double points;

    @OneToOne
    private Driver firstDriver;
    @OneToOne
    private Driver secondDriver;

}

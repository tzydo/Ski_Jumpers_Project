package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "jumper")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkiJumper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rank;
    private int bib;
    private int fisCode;
    private String name;
    private String surname;
    private String nationality;
    private double firstJump;
    private double pointsForFirstJump;
    private double secondJump;
    private double pointsForSecondJump;
    private double totalPoints;
}
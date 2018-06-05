package com.pl.skijumping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SkiJumperDTO {
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

    public SkiJumperDTO id(int id) {
        this.id = id;
        return this;
    }

    public SkiJumperDTO rank(int rank) {
        this.rank = rank;
        return this;
    }

    public SkiJumperDTO bib(int bib) {
        this.bib = bib;
        return this;
    }

    public SkiJumperDTO fisCode(int fisCode) {
        this.fisCode = fisCode;
        return this;
    }

    public SkiJumperDTO name(String name) {
        this.name = name;
        return this;
    }

    public SkiJumperDTO surname(String surname) {
        this.surname = surname;
        return this;
    }

    public SkiJumperDTO nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public SkiJumperDTO firstJump(double firstJump) {
        this.firstJump = firstJump;
        return this;
    }

    public SkiJumperDTO pointsForFirstJump(double pointsForFirstJump) {
        this.pointsForFirstJump = pointsForFirstJump;
        return this;
    }

    public SkiJumperDTO secondJump(double secondJump) {
        this.secondJump = secondJump;
        return this;
    }

    public SkiJumperDTO pointsForSecondJump(double pointsForSecondJump) {
        this.pointsForSecondJump = pointsForSecondJump;
        return this;
    }

    public SkiJumperDTO totalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }
}

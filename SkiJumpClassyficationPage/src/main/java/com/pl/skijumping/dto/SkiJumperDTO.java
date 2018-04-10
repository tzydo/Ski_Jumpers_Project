package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}

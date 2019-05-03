package com.pl.skijumping.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JumpResultDTO implements Serializable {

    private Long id;
    private Integer  rank;
    private Double firstJump;
    private Double pointsForFirstJump;
    private Double secondJump;
    private Double pointsForSecondJump;
    private Double totalPoints;
    private Integer fisCodeId;
    private String jumperName;
    private String competitorId;

    public JumpResultDTO id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultDTO firstJump(Double firstJump) {
        this.firstJump = firstJump;
        return this;
    }

    public JumpResultDTO pointsForFirstJump(Double pointsForFirstJump) {
        this.pointsForFirstJump = pointsForFirstJump;
        return this;
    }

    public JumpResultDTO secondJump(Double secondJump) {
        this.secondJump = secondJump;
        return this;
    }

    public JumpResultDTO pointsForSecondJump(Double pointsForSecondJump) {
        this.pointsForSecondJump = pointsForSecondJump;
        return this;
    }

    public JumpResultDTO totalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }

    public JumpResultDTO rank(int rank) {
        this.rank = rank;
        return this;
    }

    public JumpResultDTO fisCodeId(Integer fisCodeId) {
        this.fisCodeId = fisCodeId;
        return this;
    }

    public JumpResultDTO jumperName(String jumperName) {
        this.jumperName = jumperName;
        return this;
    }

    public JumpResultDTO competitorId(String competitorId) {
        this.competitorId = competitorId;
        return this;
    }
}

package com.pl.skijumping.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class JumpResultDTO implements Serializable {
    private Long id;
    private Long jumperId;
    private Long dataRaceId;
    private int rank;
    private double firstJump;
    private double pointsForFirstJump;
    private double secondJump;
    private double pointsForSecondJump;
    private double totalPoints;

    public JumpResultDTO id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultDTO jumperId(Long jumperId) {
        this.jumperId = jumperId;
        return this;
    }

    public JumpResultDTO firstJump(double firstJump) {
        this.firstJump = firstJump;
        return this;
    }

    public JumpResultDTO pointsForFirstJump(double pointsForFirstJump) {
        this.pointsForFirstJump = pointsForFirstJump;
        return this;
    }

    public JumpResultDTO secondJump(double secondJump) {
        this.secondJump = secondJump;
        return this;
    }

    public JumpResultDTO pointsForSecondJump(double pointsForSecondJump) {
        this.pointsForSecondJump = pointsForSecondJump;
        return this;
    }

    public JumpResultDTO totalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }
}

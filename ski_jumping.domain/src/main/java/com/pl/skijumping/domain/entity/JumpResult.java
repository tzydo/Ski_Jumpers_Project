package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "jump_result")
public class JumpResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ski_jumper_id")
    private SkiJumper skiJumper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_race_id")
    private DataRace dataRace;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "first_jump")
    private double firstJump;

    @Column(name = "points_for_first_jump")
    private double pointsForFirstJump;

    @Column(name = "second_jump")
    private double secondJump;

    @Column(name = "points_for_second_jump")
    private double pointsForSecondJump;

    @Column(name = "total_points")
    private double totalPoints;

    public JumpResult id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResult skiJumper(SkiJumper skiJumper) {
        this.skiJumper = skiJumper;
        return this;
    }

    public JumpResult dataRace(DataRace dataRace) {
        this.dataRace = dataRace;
        return this;
    }

    public JumpResult rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public JumpResult firstJump(double firstJump) {
        this.firstJump = firstJump;
        return this;
    }

    public JumpResult pointsForFirstJump(double pointsForFirstJump) {
        this.pointsForFirstJump = pointsForFirstJump;
        return this;
    }

    public JumpResult secondJump(double secondJump) {
        this.secondJump = secondJump;
        return this;
    }

    public JumpResult pointsForSecondJump(double pointsForSecondJump) {
        this.pointsForSecondJump = pointsForSecondJump;
        return this;
    }

    public JumpResult totalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }
}

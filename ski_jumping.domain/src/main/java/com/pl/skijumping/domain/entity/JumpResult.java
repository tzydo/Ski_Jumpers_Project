package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jump_result")
public class JumpResult implements Serializable {

    private Long id;
    private SkiJumper skiJumper;
    private Integer rank;
    private double firstJump;
    private double pointsForFirstJump;
    private double secondJump;
    private double pointsForSecondJump;
    private double totalPoints;
    private JumpResultToDataRace jumpResultToDataRace;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ski_jumper_id")
    public SkiJumper getSkiJumper() {
        return skiJumper;
    }

    public void setSkiJumper(SkiJumper skiJumper) {
        this.skiJumper = skiJumper;
    }

    @Column(name = "rank", nullable = false, unique = true)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Column(name = "first_jump")
    public double getFirstJump() {
        return firstJump;
    }

    public void setFirstJump(double firstJump) {
        this.firstJump = firstJump;
    }

    @Column(name = "points_for_first_jump")
    public double getPointsForFirstJump() {
        return pointsForFirstJump;
    }

    public void setPointsForFirstJump(double pointsForFirstJump) {
        this.pointsForFirstJump = pointsForFirstJump;
    }

    @Column(name = "second_jump")
    public double getSecondJump() {
        return secondJump;
    }

    public void setSecondJump(double secondJump) {
        this.secondJump = secondJump;
    }

    @Column(name = "points_for_second_jump")
    public double getPointsForSecondJump() {
        return pointsForSecondJump;
    }

    public void setPointsForSecondJump(double pointsForSecondJump) {
        this.pointsForSecondJump = pointsForSecondJump;
    }

    @Column(name = "total_points")
    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    @OneToOne(mappedBy = "jumpResult")
    public JumpResultToDataRace getJumpResultToDataRace() {
        return jumpResultToDataRace;
    }

    public void setJumpResultToDataRace(JumpResultToDataRace jumpResultToDataRace) {
        this.jumpResultToDataRace = jumpResultToDataRace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JumpResult that = (JumpResult) o;

        if (Double.compare(that.firstJump, firstJump) != 0) return false;
        if (Double.compare(that.pointsForFirstJump, pointsForFirstJump) != 0) return false;
        if (Double.compare(that.secondJump, secondJump) != 0) return false;
        if (Double.compare(that.pointsForSecondJump, pointsForSecondJump) != 0) return false;
        if (Double.compare(that.totalPoints, totalPoints) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (skiJumper != null ? !skiJumper.equals(that.skiJumper) : that.skiJumper != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        return jumpResultToDataRace != null ? jumpResultToDataRace.equals(that.jumpResultToDataRace) : that.jumpResultToDataRace == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (skiJumper != null ? skiJumper.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        temp = Double.doubleToLongBits(firstJump);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pointsForFirstJump);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(secondJump);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pointsForSecondJump);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalPoints);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (jumpResultToDataRace != null ? jumpResultToDataRace.hashCode() : 0);
        return result;
    }

    public JumpResult id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResult skiJumper(SkiJumper skiJumper) {
        this.skiJumper = skiJumper;
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

    public JumpResult jumpResultToDataRace(JumpResultToDataRace jumpResultToDataRace) {
        this.jumpResultToDataRace = jumpResultToDataRace;
        return this;
    }
}

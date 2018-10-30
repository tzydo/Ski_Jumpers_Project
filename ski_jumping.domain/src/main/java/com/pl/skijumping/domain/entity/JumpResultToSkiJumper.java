package com.pl.skijumping.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jump_result_to_ski_jumper")
public class JumpResultToSkiJumper implements Serializable {

    private Long id;
    private JumpResult jumpResult;
    private SkiJumper skiJumper;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jump_result_id", nullable = false, unique = true)
    public JumpResult getJumpResult() {
        return jumpResult;
    }

    public void setJumpResult(JumpResult jumpResult) {
        this.jumpResult = jumpResult;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ski_jumper_id", nullable = false)
    public SkiJumper getSkiJumper() {
        return skiJumper;
    }

    public void setSkiJumper(SkiJumper skiJumper) {
        this.skiJumper = skiJumper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JumpResultToSkiJumper that = (JumpResultToSkiJumper) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (jumpResult != null ? !jumpResult.equals(that.jumpResult) : that.jumpResult != null) return false;
        return skiJumper != null ? skiJumper.equals(that.skiJumper) : that.skiJumper == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (jumpResult != null ? jumpResult.hashCode() : 0);
        result = 31 * result + (skiJumper != null ? skiJumper.hashCode() : 0);
        return result;
    }

    public JumpResultToSkiJumper id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultToSkiJumper jumpResult(JumpResult jumpResult) {
        this.jumpResult = jumpResult;
        return this;
    }

    public JumpResultToSkiJumper skiJumper(SkiJumper skiJumper) {
        this.skiJumper = skiJumper;
        return this;
    }
}

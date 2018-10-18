package com.pl.skijumping.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jump_result_to_data_race")
public class JumpResultToDataRace implements Serializable {

    private Long id;
    private JumpResult jumpResult;
    private DataRace dataRace;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jump_result", referencedColumnName = "id", nullable = false, unique = true)
    public JumpResult getJumpResult() {
        return jumpResult;
    }

    public void setJumpResult(JumpResult jumpResult) {
        this.jumpResult = jumpResult;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_race", nullable = false)
    public DataRace getDataRace() {
        return dataRace;
    }

    public void setDataRace(DataRace dataRace) {
        this.dataRace = dataRace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JumpResultToDataRace that = (JumpResultToDataRace) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (jumpResult != null ? !jumpResult.equals(that.jumpResult) : that.jumpResult != null) return false;
        return dataRace != null ? dataRace.equals(that.dataRace) : that.dataRace == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (jumpResult != null ? jumpResult.hashCode() : 0);
        result = 31 * result + (dataRace != null ? dataRace.hashCode() : 0);
        return result;
    }

    public JumpResultToDataRace id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultToDataRace jumpResult(JumpResult jumpResult) {
        this.jumpResult = jumpResult;
        return this;
    }

    public JumpResultToDataRace dataRace(DataRace dataRace) {
        this.dataRace = dataRace;
        return this;
    }
}

package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competition_type")
public class CompetitionType implements Serializable {

    private Long id;
    private String type;
    private Set<DataRace> raceList = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "type", unique = true, nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "competitionType")
    public Set<DataRace> getRaceList() {
        return raceList;
    }

    public void setRaceList(Set<DataRace> raceList) {
        this.raceList = raceList;
    }

    public void addDataRace(DataRace dataRace) {
        if(dataRace == null) return;
        this.raceList.add(dataRace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionType that = (CompetitionType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return raceList != null ? raceList.equals(that.raceList) : that.raceList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (raceList != null ? raceList.hashCode() : 0);
        return result;
    }

    public CompetitionType id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionType type(String type) {
        this.type = type;
        return this;
    }

    public CompetitionType raceList(Set<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

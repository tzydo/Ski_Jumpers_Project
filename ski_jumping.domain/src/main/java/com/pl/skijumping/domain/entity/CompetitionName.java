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
@Table(name = "competition_name")
public class CompetitionName implements Serializable {

    private Long id;
    private String name;
    private Set<DataRace> raceList = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "competitionType")
    public Set<DataRace> getRaceList() {
        return raceList;
    }

    public void setRaceList(Set<DataRace> raceList) {
        this.raceList = raceList;
    }

    public void addDataRace(DataRace dataRace){
        if(dataRace == null) return;
        this.raceList.add(dataRace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionName that = (CompetitionName) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return raceList != null ? raceList.equals(that.raceList) : that.raceList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (raceList != null ? raceList.hashCode() : 0);
        return result;
    }

    public CompetitionName id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionName name(String name) {
        this.name = name;
        return this;
    }

    public CompetitionName raceList(Set<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

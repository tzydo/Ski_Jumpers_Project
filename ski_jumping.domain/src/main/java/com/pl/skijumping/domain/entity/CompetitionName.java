package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competition_name")
public class CompetitionName implements Serializable {

    private Long id;
    private String name;
    private List<DataRace> dataRaceList = new ArrayList<>();

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
    public List<DataRace> getDataRaceList() {
        return dataRaceList;
    }

    public void setDataRaceList(List<DataRace> dataRaceList) {
        this.dataRaceList = dataRaceList;
    }

    public void addDataRace(DataRace dataRace){
        if(dataRace == null) return;
        this.dataRaceList.add(dataRace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionName that = (CompetitionName) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return dataRaceList != null ? dataRaceList.equals(that.dataRaceList) : that.dataRaceList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dataRaceList != null ? dataRaceList.hashCode() : 0);
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

    public CompetitionName dataRaceList(List<DataRace> dataRaceList) {
        this.dataRaceList = dataRaceList;
        return this;
    }
}

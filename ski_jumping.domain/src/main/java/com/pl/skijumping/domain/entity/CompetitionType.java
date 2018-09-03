package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competition_type")
public class CompetitionType implements Serializable {

    private Long id;
    private String type;
    private List<DataRace> dataRaceList = new ArrayList<>();

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
    public List<DataRace> getDataRaceList() {
        return dataRaceList;
    }

    public void setDataRaceList(List<DataRace> dataRaceList) {
        this.dataRaceList = dataRaceList;
    }

    public void addDataRace(DataRace dataRace) {
        if(dataRace == null) return;
        this.dataRaceList.add(dataRace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionType that = (CompetitionType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return dataRaceList != null ? dataRaceList.equals(that.dataRaceList) : that.dataRaceList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dataRaceList != null ? dataRaceList.hashCode() : 0);
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

    public CompetitionType dataRaceList(List<DataRace> dataRaceList) {
        this.dataRaceList = dataRaceList;
        return this;
    }
}

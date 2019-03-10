package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_race_to_place")
public class DataRaceToPlace implements Serializable {
    private Long id;
    private DataRace dataRace;
    private Place place;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_race_id", nullable = false, unique = true)
    public DataRace getDataRace() {
        return dataRace;
    }

    public void setDataRace(DataRace dataRace) {
        this.dataRace = dataRace;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRaceToPlace that = (DataRaceToPlace) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dataRace, that.dataRace) &&
                Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataRace, place);
    }

    public DataRaceToPlace id(Long id) {
        this.id = id;
        return this;
    }

    public DataRaceToPlace dataRace(DataRace dataRace) {
        this.dataRace = dataRace;
        return this;
    }

    public DataRaceToPlace place(Place place) {
        this.place = place;
        return this;
    }
}

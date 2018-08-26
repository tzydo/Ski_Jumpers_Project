package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRaceDTO implements Serializable {
    private Long id;
    private LocalDate date;
    private String city;
    private String shortCountryName;
    private String competitionName;
    private String competitionType;
    private Long raceId;

    public DataRaceDTO id(Long id) {
        this.id = id;
        return this;
    }

    public DataRaceDTO date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DataRaceDTO city(String city) {
        this.city = city;
        return this;
    }

    public DataRaceDTO shortCountryName(String shortCountryName) {
        this.shortCountryName = shortCountryName;
        return this;
    }

    public DataRaceDTO competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public DataRaceDTO competitionType(String competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public DataRaceDTO raceId(Long raceId) {
        this.raceId = raceId;
        return this;
    }
}

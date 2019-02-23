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
    private Boolean isCancelled;
    private Integer jumpCategoryId;
    private String codex;
    private String gender;
    private LocalDate date;
    private String competitionType;
    private Long raceId;
    private Long eventId;
    private int seasonCode;

    public DataRaceDTO id(Long id) {
        this.id = id;
        return this;
    }

    public DataRaceDTO date(LocalDate date) {
        this.date = date;
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

    public DataRaceDTO isCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
        return this;
    }

    public DataRaceDTO eventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }


    public DataRaceDTO jumpCategoryId(Integer jumpCategoryId) {
        this.jumpCategoryId = jumpCategoryId;
        return this;
    }

    public DataRaceDTO seasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
        return this;
    }


    public DataRaceDTO codex(String codex) {
        this.codex = codex;
        return this;
    }


    public DataRaceDTO gender(String gender) {
        this.gender = gender;
        return this;
    }
}

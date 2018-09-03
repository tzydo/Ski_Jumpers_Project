package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkiJumperDTO implements Serializable {

    private Long id;
    private int bib;
    private int fisCode;
    private String name;
    private LocalDate birthday;
    private String nationality;
    private String skiClub;
    private String team;
    private String gender;
    private String martialStatus;
    private List<Long> jumpResultIds = new ArrayList<>();

    public SkiJumperDTO id(Long id) {
        this.id = id;
        return this;
    }

    public SkiJumperDTO bib(int bib) {
        this.bib = bib;
        return this;
    }

    public SkiJumperDTO fisCode(int fisCode) {
        this.fisCode = fisCode;
        return this;
    }

    public SkiJumperDTO name(String name) {
        this.name = name;
        return this;
    }

    public SkiJumperDTO nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public SkiJumperDTO skiClub(String skiClub) {
        this.skiClub = skiClub;
        return this;
    }

    public SkiJumperDTO team(String team) {
        this.team = team;
        return this;
    }

    public SkiJumperDTO martialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
        return this;
    }

    public SkiJumperDTO birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public SkiJumperDTO gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void addJumpResult(Long jumpResultId) {
        if(jumpResultId == null) return;
        this.jumpResultIds.add(jumpResultId);
    }

    public SkiJumperDTO jumpResultList(List<Long> jumpResultIds) {
        this.jumpResultIds = jumpResultIds;
        return this;
    }
}

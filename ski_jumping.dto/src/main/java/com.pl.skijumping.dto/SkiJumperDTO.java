package com.pl.skijumping.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SkiJumperDTO {
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
    private List<JumpResultDTO> jumpResult;

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

    public SkiJumperDTO jumpResult(List<JumpResultDTO> jumpResult) {
        this.jumpResult = jumpResult;
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
}

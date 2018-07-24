package com.pl.skijumping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SkiJumperDTO {
    private int id;
    private int bib;
    private int fisCode;
    private String name;
    private String nationality;
    private JumpResultDTO jumpResult;

    public SkiJumperDTO id(int id) {
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


    public SkiJumperDTO jumpResult(JumpResultDTO jumpResult) {
        this.jumpResult = jumpResult;
        return this;
    }
}

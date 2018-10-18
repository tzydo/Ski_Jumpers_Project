package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentYearDTO implements Serializable {

    private Long id;
    private String year;

    public TournamentYearDTO id(Long id) {
        this.id = id;
        return this;
    }

    public TournamentYearDTO year(String year) {
        this.year = year;
        return this;
    }
}

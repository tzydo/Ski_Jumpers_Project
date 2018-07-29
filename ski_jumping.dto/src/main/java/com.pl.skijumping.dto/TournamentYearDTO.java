package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TournamentYearDTO {
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

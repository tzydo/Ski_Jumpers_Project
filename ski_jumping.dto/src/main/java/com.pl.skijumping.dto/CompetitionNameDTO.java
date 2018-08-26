package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionNameDTO implements Serializable {
    private Long id;
    private String name;

    public CompetitionNameDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionNameDTO name(String name) {
        this.name = name;
        return this;
    }
}

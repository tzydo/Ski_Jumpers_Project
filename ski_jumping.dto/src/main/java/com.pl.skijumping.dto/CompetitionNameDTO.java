package com.pl.skijumping.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CompetitionNameDTO {
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

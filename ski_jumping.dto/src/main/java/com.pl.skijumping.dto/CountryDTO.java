package com.pl.skijumping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CountryDTO {
    private int id;
    private String name;

    public CountryDTO id(int id) {
        this.id = id;
        return this;
    }

    public CountryDTO name(String name) {
        this.name = name;
        return this;
    }
}

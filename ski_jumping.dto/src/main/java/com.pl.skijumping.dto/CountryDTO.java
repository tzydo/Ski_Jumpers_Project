package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private Integer id;
    private String name;
    private String shortName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDTO that = (CountryDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName);
    }

    public CountryDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public CountryDTO name(String name) {
        this.name = name;
        return this;
    }

    public CountryDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }
}

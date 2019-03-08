package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {
    private Integer id;
    private Integer country;
    private String city;
    private Integer hillSize;
    private String hillType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceDTO placeDTO = (PlaceDTO) o;
        return Objects.equals(id, placeDTO.id) &&
                Objects.equals(country, placeDTO.country) &&
                Objects.equals(city, placeDTO.city) &&
                Objects.equals(hillSize, placeDTO.hillSize) &&
                Objects.equals(hillType, placeDTO.hillType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, hillSize, hillType);
    }

    public PlaceDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public PlaceDTO country(Integer country) {
        this.country = country;
        return this;
    }

    public PlaceDTO city(String city) {
        this.city = city;
        return this;
    }

    public PlaceDTO hillSize(Integer hillSize) {
        this.hillSize = hillSize;
        return this;
    }

    public PlaceDTO hillType(String hillType) {
        this.hillType = hillType;
        return this;
    }
}



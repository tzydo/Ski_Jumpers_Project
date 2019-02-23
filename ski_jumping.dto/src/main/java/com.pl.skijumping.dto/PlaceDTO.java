package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {
    private Integer id;
    private Integer countryId;
    private Integer hillSize;
    private HillType hillType;

    public PlaceDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public PlaceDTO countryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }

    public PlaceDTO hillSize(Integer hillSize) {
        this.hillSize = hillSize;
        return this;
    }

    public PlaceDTO hillType(HillType hillType) {
        this.hillType = hillType;
        return this;
    }
}



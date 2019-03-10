package com.pl.skijumping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRaceToPlaceDTO {
    private Long id;
    private Integer placeId;
    private Long dataRaceId;

    public DataRaceToPlaceDTO id(Long id) {
        this.id = id;
        return this;
    }

    public DataRaceToPlaceDTO placeId(Integer placeId) {
        this.placeId = placeId;
        return this;
    }

    public DataRaceToPlaceDTO dataRaceId(Long dataRaceId) {
        this.dataRaceId = dataRaceId;
        return this;
    }
}

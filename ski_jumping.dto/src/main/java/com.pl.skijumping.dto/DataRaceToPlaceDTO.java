package com.pl.skijumping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRaceToPlaceDTO {
    private Integer id;
    private PlaceDTO placeDTO;
    private DataRaceDTO dataRaceDTO;


    public DataRaceToPlaceDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public DataRaceToPlaceDTO placeDTO(PlaceDTO placeDTO) {
        this.placeDTO = placeDTO;
        return this;
    }

    public DataRaceToPlaceDTO dataRaceDTO(DataRaceDTO dataRaceDTO) {
        this.dataRaceDTO = dataRaceDTO;
        return this;
    }
}

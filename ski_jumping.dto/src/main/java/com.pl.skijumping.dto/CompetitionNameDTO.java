package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionNameDTO implements Serializable {

    private Long id;
    private String name;
    private List<Long> dataRaceId = new ArrayList<>();

    public CompetitionNameDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionNameDTO name(String name) {
        this.name = name;
        return this;
    }

    public CompetitionNameDTO dataRaceList(List<Long> dataRaceId) {
        this.dataRaceId = dataRaceId;
        return this;
    }

    public void addDataRaceId(Long dataRaceId) {
        if(dataRaceId != null) {
            this.dataRaceId.add(dataRaceId);
        }
    }
}

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
public class CompetitionTypeDTO implements Serializable {

    private Long id;
    private String type;
    private List<Long> dataRaceId = new ArrayList<>();

    public CompetitionTypeDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionTypeDTO type(String type) {
        this.type = type;
        return this;
    }

    public CompetitionTypeDTO dataRaceList(List<Long> dataRaceId) {
        this.dataRaceId = dataRaceId;
        return this;
    }

    public void addDataRaceId(Long dataRaceId) {
        if(dataRaceId != null) {
            this.dataRaceId.add(dataRaceId);
        }
    }
}

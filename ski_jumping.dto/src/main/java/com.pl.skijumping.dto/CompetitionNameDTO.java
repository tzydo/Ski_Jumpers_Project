package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionNameDTO implements Serializable {
    private Long id;
    private String name;
    private Set<Long> dataRaceId = new HashSet<>();

    public CompetitionNameDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionNameDTO name(String name) {
        this.name = name;
        return this;
    }

    public CompetitionNameDTO dataRaceId(Set<Long> dataRaceId) {
        this.dataRaceId = dataRaceId;
        return this;
    }

    public void addDataRaceId(Long dataRaceId) {
        if(dataRaceId != null) {
            this.dataRaceId.add(dataRaceId);
        }
    }
}

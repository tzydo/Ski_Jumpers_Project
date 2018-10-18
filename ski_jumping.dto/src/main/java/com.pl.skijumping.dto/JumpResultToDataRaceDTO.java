package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JumpResultToDataRaceDTO {

    private Long id;
    private Long jumpResultId;
    private Long dataRaceId;

    public JumpResultToDataRaceDTO id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultToDataRaceDTO jumpResultId(Long jumpResultId) {
        this.jumpResultId = jumpResultId;
        return this;
    }

    public JumpResultToDataRaceDTO dataRaceId(Long dataRaceId) {
        this.dataRaceId = dataRaceId;
        return this;
    }
}

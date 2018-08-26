package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionTypeDTO implements Serializable {
    private Long id;
    private String type;

    public CompetitionTypeDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionTypeDTO type(String type) {
        this.type = type;
        return this;
    }
}

package com.pl.skijumping.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CompetitionTypeDTO {
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

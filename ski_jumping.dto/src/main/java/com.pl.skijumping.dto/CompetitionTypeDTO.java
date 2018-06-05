package com.pl.skijumping.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class CompetitionTypeDTO {
    private Long id;
    private String competitionType;
    private String competitionName;
}

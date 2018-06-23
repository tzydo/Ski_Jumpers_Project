package com.pl.skijumping.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class CompetitionNameDTO {
    private Long id;
    private String name;
}

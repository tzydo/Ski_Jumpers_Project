package com.pl.skijumping.domain.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@Table(name = "Competition_Type")
public class CompetitionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @OneToMany(mappedBy = "competitionType")
    private Long id;

    @NotNull
    @Column(name = "competition_type")
    private String competitionType;

    @NotNull
    @Column(name = "competition_name")
    private String competitionName;
}

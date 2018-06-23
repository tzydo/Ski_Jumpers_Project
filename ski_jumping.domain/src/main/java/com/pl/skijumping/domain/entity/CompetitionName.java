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
@Table(name = "Competition_Name")
public class CompetitionName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @OneToMany(mappedBy = "type")
    private Long id;

    @NotNull
    @Column(name = "competition_name")
    private String competitionName;
}

package com.pl.skijumping.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "Competition_Type")
public class CompetitionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "competition_type")
    private String competitionType;

    @OneToMany(mappedBy = "competitionType")
    private List<DataRace> raceList;

    public CompetitionType id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionType competitionType(String competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public CompetitionType raceList(List<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

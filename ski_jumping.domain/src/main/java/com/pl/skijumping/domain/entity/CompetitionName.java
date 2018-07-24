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
@Table(name = "Competition_Name")
public class CompetitionName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "competition_name")
    private String competitionName;

    @OneToMany(mappedBy = "competitionName")
    private List<DataRace> raceList;

    public CompetitionName id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionName competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public CompetitionName raceList(List<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

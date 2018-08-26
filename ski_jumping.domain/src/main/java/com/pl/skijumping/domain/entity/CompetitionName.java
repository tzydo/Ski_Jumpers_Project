package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "competition_name")
public class CompetitionName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "competitionType")
    private Set<DataRace> raceList;

    public CompetitionName id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionName name(String name) {
        this.name = name;
        return this;
    }

    public CompetitionName raceList(Set<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

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
@Table(name = "competition_type")
public class CompetitionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "competitionType")
    private Set<DataRace> raceList;

    public CompetitionType id(Long id) {
        this.id = id;
        return this;
    }

    public CompetitionType type(String type) {
        this.type = type;
        return this;
    }

    public CompetitionType raceList(Set<DataRace> raceList) {
        this.raceList = raceList;
        return this;
    }
}

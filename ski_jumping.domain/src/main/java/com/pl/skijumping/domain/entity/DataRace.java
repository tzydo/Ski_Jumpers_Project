package com.pl.skijumping.domain.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "Data_Race")
public class DataRace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "short_country_name", nullable = false)
    private String shortCountryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_type_id")
    private CompetitionType competitionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_name_id")
    private CompetitionName competitionName;

    @Column(name = "race_id", nullable = false, unique = true)
    private Long raceId;

    public DataRace id(Long id) {
        this.id = id;
        return this;
    }

    public DataRace date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DataRace city(String city) {
        this.city = city;
        return this;
    }

    public DataRace shortCountryName(String shortCountryName) {
        this.shortCountryName = shortCountryName;
        return this;
    }

    public DataRace competitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public DataRace competitionName(CompetitionName competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public DataRace raceId(Long raceId) {
        this.raceId = raceId;
        return this;
    }
}
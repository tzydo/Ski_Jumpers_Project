package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_race")
public class DataRace implements Serializable {

    private Long id;
    private LocalDate date;
    private String city;
    private String shortCountryName;
    private CompetitionType competitionType;
    private CompetitionName competitionName;
    private Long raceId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "short_country_name", nullable = false)
    public String getShortCountryName() {
        return shortCountryName;
    }

    public void setShortCountryName(String shortCountryName) {
        this.shortCountryName = shortCountryName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_type_id")
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_name_id")
    public CompetitionName getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(CompetitionName competitionName) {
        this.competitionName = competitionName;
    }

    @Column(name = "race_id", nullable = false, unique = true)
    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataRace dataRace = (DataRace) o;

        if (id != null ? !id.equals(dataRace.id) : dataRace.id != null) return false;
        if (date != null ? !date.equals(dataRace.date) : dataRace.date != null) return false;
        if (city != null ? !city.equals(dataRace.city) : dataRace.city != null) return false;
        if (shortCountryName != null ? !shortCountryName.equals(dataRace.shortCountryName) : dataRace.shortCountryName != null)
            return false;
        if (competitionType != null ? !competitionType.equals(dataRace.competitionType) : dataRace.competitionType != null)
            return false;
        if (competitionName != null ? !competitionName.equals(dataRace.competitionName) : dataRace.competitionName != null)
            return false;
        return raceId != null ? raceId.equals(dataRace.raceId) : dataRace.raceId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (shortCountryName != null ? shortCountryName.hashCode() : 0);
        result = 31 * result + (competitionType != null ? competitionType.hashCode() : 0);
        result = 31 * result + (competitionName != null ? competitionName.hashCode() : 0);
        result = 31 * result + (raceId != null ? raceId.hashCode() : 0);
        return result;
    }

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
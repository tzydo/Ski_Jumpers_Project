package com.pl.skijumping.domain.entity;

import com.pl.skijumping.domain.model.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "data_race")
public class DataRace implements Serializable {

    private Long id;
    private Boolean isCancelled;
    private JumpCategory jumpCategory;
    private String codex;
    private Gender gender;
    private LocalDate date;
    private CompetitionType competitionType;
    private Long raceId;
    private Long eventId;
    private int seasonCode;
    private List<JumpResultToDataRace> jumpResultToDataRaces;
    private List<DataRaceToPlace> dataRaceToPlaces;

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

    @Column(name = "cancelled")
    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jump_category")
    public JumpCategory getJumpCategory() {
        return jumpCategory;
    }

    public void setJumpCategory(JumpCategory jumpCategory) {
        this.jumpCategory = jumpCategory;
    }

    @Column(name = "codex")
    public String getCodex() {
        return codex;
    }

    public void setCodex(String codex) {
        this.codex = codex;
    }

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_type_id")
    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    @Column(name = "race_id", nullable = false, unique = true)
    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    @Column(name = "event_id")
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Column(name = "season_code")
    public int getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
    }

    @OneToMany(mappedBy = "dataRace")
    public List<JumpResultToDataRace> getJumpResultToDataRaces() {
        return jumpResultToDataRaces;
    }

    public void setJumpResultToDataRaces(List<JumpResultToDataRace> jumpResultToDataRaces) {
        this.jumpResultToDataRaces = jumpResultToDataRaces;
    }

    @OneToMany(mappedBy = "dataRace")
    public List<DataRaceToPlace> getDataRaceToPlaces() {
        return dataRaceToPlaces;
    }

    public void setDataRaceToPlaces(List<DataRaceToPlace> dataRaceToPlaces) {
        this.dataRaceToPlaces = dataRaceToPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRace dataRace = (DataRace) o;
        return seasonCode == dataRace.seasonCode &&
                Objects.equals(id, dataRace.id) &&
                Objects.equals(isCancelled, dataRace.isCancelled) &&
                Objects.equals(jumpCategory, dataRace.jumpCategory) &&
                Objects.equals(codex, dataRace.codex) &&
                gender == dataRace.gender &&
                Objects.equals(date, dataRace.date) &&
                Objects.equals(competitionType, dataRace.competitionType) &&
                Objects.equals(raceId, dataRace.raceId) &&
                Objects.equals(eventId, dataRace.eventId) &&
                Objects.equals(jumpResultToDataRaces, dataRace.jumpResultToDataRaces) &&
                Objects.equals(dataRaceToPlaces, dataRace.dataRaceToPlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isCancelled, jumpCategory, codex, gender, date, competitionType, raceId, eventId, seasonCode, jumpResultToDataRaces, dataRaceToPlaces);
    }

    public DataRace id(Long id) {
        this.id = id;
        return this;
    }

    public DataRace isCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
        return this;
    }

    public DataRace jumpCategory(JumpCategory jumpCategory) {
        this.jumpCategory = jumpCategory;
        return this;
    }

    public DataRace codex(String codex) {
        this.codex = codex;
        return this;
    }

    public DataRace gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public DataRace date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DataRace competitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public DataRace raceId(Long raceId) {
        this.raceId = raceId;
        return this;
    }

    public DataRace eventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public DataRace seasonCode(int seasonCode) {
        this.seasonCode = seasonCode;
        return this;
    }

    public DataRace jumpResultToDataRaces(List<JumpResultToDataRace> jumpResultToDataRaces) {
        this.jumpResultToDataRaces = jumpResultToDataRaces;
        return this;
    }

    public DataRace dataRaceToPlaces(List<DataRaceToPlace> dataRaceToPlaces) {
        this.dataRaceToPlaces = dataRaceToPlaces;
        return this;
    }
}
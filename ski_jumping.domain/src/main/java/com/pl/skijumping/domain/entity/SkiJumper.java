package com.pl.skijumping.domain.entity;

import com.pl.skijumping.domain.model.Gender;
import com.pl.skijumping.domain.model.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Jumper")
@NoArgsConstructor
@AllArgsConstructor
public class SkiJumper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bib")
    private int bib;

    @Column(name = "fis_code")
    private int fisCode;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "ski_club")
    private String skiClub;

    @Column(name = "team")
    private String team;

    @OneToMany(mappedBy = "skiJumper")
    private List<JumpResult> jumpResult;

    @Column(name = "martial_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus martialStatus;

    public SkiJumper id(Long id) {
        this.id = id;
        return this;
    }

    public SkiJumper bib(int bib) {
        this.bib = bib;
        return this;
    }

    public SkiJumper fisCode(int fisCode) {
        this.fisCode = fisCode;
        return this;
    }

    public SkiJumper name(String name) {
        this.name = name;
        return this;
    }

    public SkiJumper birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public SkiJumper nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public SkiJumper skiClub(String skiClub) {
        this.skiClub = skiClub;
        return this;
    }

    public SkiJumper team(String team) {
        this.team = team;
        return this;
    }

    public SkiJumper jumpResult(List<JumpResult> jumpResult) {
        this.jumpResult = jumpResult;
        return this;
    }

    public SkiJumper gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public SkiJumper martialStatus(MaritalStatus martialStatus) {
        this.martialStatus = martialStatus;
        return this;
    }
}
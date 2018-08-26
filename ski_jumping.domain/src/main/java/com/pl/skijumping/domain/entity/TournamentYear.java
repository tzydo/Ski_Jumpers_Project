package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tournament_year")
public class TournamentYear implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 4)
    private String year;

    public TournamentYear id(Long id) {
        this.id = id;
        return this;
    }

    public TournamentYear year(String year) {
        this.year = year;
        return this;
    }
}

package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tournament_year")
public class TournamentYear implements Serializable {

    private Long id;
    private String year;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true, length = 4)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TournamentYear that = (TournamentYear) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return year != null ? year.equals(that.year) : that.year == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    public TournamentYear id(Long id) {
        this.id = id;
        return this;
    }

    public TournamentYear year(String year) {
        this.year = year;
        return this;
    }
}

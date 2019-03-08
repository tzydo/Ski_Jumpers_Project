package com.pl.skijumping.domain.entity;

import com.pl.skijumping.domain.model.HillType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "place")
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    private Integer id;
    private Country country;
    private String city;
    private Integer hillSize;
    private HillType hillType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "hill_size")
    public Integer getHillSize() {
        return hillSize;
    }

    public void setHillSize(Integer hillSize) {
        this.hillSize = hillSize;
    }

    @Column(name = "hill_type")
    @Enumerated(EnumType.STRING)
    public HillType getHillType() {
        return hillType;
    }

    public void setHillType(HillType hillType) {
        this.hillType = hillType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(id, place.id) &&
                Objects.equals(country, place.country) &&
                Objects.equals(city, place.city) &&
                Objects.equals(hillSize, place.hillSize) &&
                hillType == place.hillType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, hillSize, hillType);
    }


    public Place id(Integer id) {
        this.id = id;
        return this;
    }

    public Place country(Country country) {
        this.country = country;
        return this;
    }

    public Place city(String city) {
        this.city = city;
        return this;
    }

    public Place hillSize(Integer hillSize) {
        this.hillSize = hillSize;
        return this;
    }

    public Place hillType(HillType hillType) {
        this.hillType = hillType;
        return this;
    }
}

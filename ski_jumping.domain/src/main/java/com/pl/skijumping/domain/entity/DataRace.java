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
@Builder
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

//    @ManyToOne
    @Column(name = "competition_type")
//    @JoinColumn(name = "competition_type_id")
    private Long competitionTypeId;

    @Column(name = "race_id", nullable = false, unique = true)
    private Long raceId;
}
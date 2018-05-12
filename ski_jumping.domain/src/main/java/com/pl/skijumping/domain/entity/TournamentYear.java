package com.pl.skijumping.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"year"})})
public class TournamentYear implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 4)
    private String year;
}

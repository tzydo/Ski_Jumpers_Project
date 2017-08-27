package com.pl.ski_jumping.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jumper")
public class SkiJumperEntity {
    @Id
    private int id;
    private int rank;
    private int bib;
    private String name;
    private String lastName;
    private int year;
    private String nationality;
    private double first_jump;
    private double points_for_first_jump;
    private double second_jump;
    private double points_for_second_jump;
    private double total_points;
}

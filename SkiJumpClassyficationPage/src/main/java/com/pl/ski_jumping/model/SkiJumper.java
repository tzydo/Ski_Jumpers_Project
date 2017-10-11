package com.pl.ski_jumping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jumper")
public class SkiJumper implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rank;
    private int bib;
    private int fis_code;
    private String name;
    private String surname;
    private String nationality;
    private double first_jump;
    private double points_for_first_jump;
    private double second_jump;
    private double points_for_second_jump;
    private double total_points;
}
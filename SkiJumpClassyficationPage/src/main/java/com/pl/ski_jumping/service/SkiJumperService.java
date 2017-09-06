package com.pl.ski_jumping.service;

import com.pl.ski_jumping.model.SkiJumper;

import java.util.List;

public interface SkiJumperService {
    List<SkiJumper> findAll();
    SkiJumper findByRank(int rank);
    List<SkiJumper> findByName(String name);
    void save(SkiJumper skiJumper);
    void update(SkiJumper skiJumper);
    void deleteByRank(int rank);
    void deleteAll();
}

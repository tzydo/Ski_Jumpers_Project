package com.pl.ski_jumping.service;


import com.pl.ski_jumping.model.Country;
import com.pl.ski_jumping.model.SkiJumper;

import java.util.List;

public interface SkiJumperService {
    List<SkiJumper> findAll();
    SkiJumper findByRank(int rank);
    List<SkiJumper> findByName(String name);
    List<SkiJumper> getJumpersByPattenr(String pattern);
    void save(SkiJumper skiJumper);
    void update(SkiJumper skiJumper);
    void deleteByRank(int rank);
    void deleteAll();
    int getJumpersCount();
    int getFisCode();
    List<Country> getCountries();
    List<Country> getCountriesByPattern(String pattern);
}

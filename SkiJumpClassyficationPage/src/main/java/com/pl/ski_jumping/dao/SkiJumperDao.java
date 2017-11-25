package com.pl.ski_jumping.dao;

import com.pl.ski_jumping.model.Country;
import com.pl.ski_jumping.model.SkiJumper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SkiJumperDao{

    List<SkiJumper> findAll();
    SkiJumper findByRank(int rank);
    List<SkiJumper> findByName(String name);
    List<SkiJumper> getJumperByPattern(String pattern);
    void save(SkiJumper skiJumper);
    void update(SkiJumper skiJumper);
    @Transactional
    void deleteByRank(int rank);
    @Transactional
    void deleteAll();
    int getJumpersCount();
    List<Integer> getFisCodeList();
    List<Country> getCountries();
    List<Country> getCountriesByPattern(String pattern);
}

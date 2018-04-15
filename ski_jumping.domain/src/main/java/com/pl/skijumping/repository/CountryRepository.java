package com.pl.skijumping.repository;

import com.pl.skijumping.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findAllByName(String name);
//    List<Country> getDistinctByName();
//    @Query("SELECT c FROM Country c where :pattern")
//    List<Country> getCountriesByPattern(@Param("pattern")String pattern);
}
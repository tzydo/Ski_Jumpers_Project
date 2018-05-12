package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.TournamentYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentYearRepository extends JpaRepository<TournamentYear, Long> {
    TournamentYear findByYear(String year);
}

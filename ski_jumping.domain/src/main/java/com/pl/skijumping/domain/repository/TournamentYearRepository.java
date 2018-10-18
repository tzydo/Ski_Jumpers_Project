package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.TournamentYear;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentYearRepository extends JpaRepository<TournamentYear, Long> {
    TournamentYear findByYear(String year);

    List<TournamentYear> findAllByOrderByYearDesc(Pageable limit);
}

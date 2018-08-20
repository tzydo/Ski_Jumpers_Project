package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.TournamentYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentYearRepository extends JpaRepository<TournamentYear, Long> {
    TournamentYear findByYear(String year);

    @Query(nativeQuery = true, value = "SELECT Top :limit * FROM Tournament_Year ORDER BY year DESC")
    List<TournamentYear> findAllByTopAndLimit(@Param("limit") Integer limit);
}

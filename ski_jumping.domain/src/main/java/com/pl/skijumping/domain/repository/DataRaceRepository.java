package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.DataRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRaceRepository extends JpaRepository<DataRace, Long>, QueryDslPredicateExecutor {
}

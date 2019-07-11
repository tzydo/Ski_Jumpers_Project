package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.DataRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DataRaceRepository extends JpaRepository<DataRace, Long>, QueryDslPredicateExecutor {

    @Transactional
    DataRace findByRaceId(Long raceId);

    @Transactional
    DataRace findByEventId(Long eventId);
}

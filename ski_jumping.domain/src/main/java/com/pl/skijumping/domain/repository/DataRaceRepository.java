package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.DataRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DataRaceRepository extends JpaRepository<DataRace, Long>, QueryDslPredicateExecutor {
    @Query("select data.raceId from DataRace data")
    List<Long> getRaceDataList();

    @Transactional
    List<DataRace> findAllByIdIn(List<Long> dataRaceIds);

    DataRace findByRaceId(Long raceId);
}

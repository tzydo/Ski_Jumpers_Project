package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.JumpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JumpResultRepository extends JpaRepository<JumpResult, Long>, QueryDslPredicateExecutor {
//    JumpResult findByDataRace_IdAndSkiJumper_IdAndRank(Long dataRaceId, Long skiJumperId, Integer rank);

    List<JumpResult> findAllByIdIn(List<Long> jumpResultIds);
}

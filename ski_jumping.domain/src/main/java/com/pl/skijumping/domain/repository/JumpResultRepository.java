package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.JumpResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JumpResultRepository extends JpaRepository<JumpResult, Long>, QueryDslPredicateExecutor {
//    JumpResult findByDataRace_IdAndSkiJumper_IdAndRank(Long dataRaceId, Long skiJumperId, Integer rank);

    @Query(value =
            "SELECT jump.id FROM com.pl.skijumping.domain.entity.JumpResultToDataRace jr " +
                    "JOIN jr.jumpResult jr_j " +
                    "JOIN jr.dataRace jr_d " +
                    "JOIN JumpResult jump ON jump.id = jr_j.id " +
            "Where jump.rank = :rank AND  jr_d.race_id = :dataRaceId")
    JumpResult findJumpResultByRankAndDataRace(@Param("rank")Integer rankId, @Param("dataRaceId")Long dataRaceId);

    List<JumpResult> findAllByIdIn(List<Long> jumpResultIds);
}

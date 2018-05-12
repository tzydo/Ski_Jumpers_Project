package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.SkiJumper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkiJumperRepository extends JpaRepository<SkiJumper, Integer> {

    List<SkiJumper> findAll();
    SkiJumper findByRank(int rank);
    List<SkiJumper> findAllByName(String name);
    void deleteAllByRank(int rank);
    void deleteAll();

//    @Query("SELECT COUNT(j) FROM SkiJumper j")
//    Integer getJumpersCount();

//    @Query("SELECT j FROM SkiJumper j where :pattern")
//    List<SkiJumper> getJumpersByPattenr(String pattern);
}
//    int getJumpersCount();
//    int getFisCode();
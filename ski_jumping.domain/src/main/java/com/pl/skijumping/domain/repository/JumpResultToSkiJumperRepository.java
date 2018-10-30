package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.JumpResultToSkiJumper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JumpResultToSkiJumperRepository extends JpaRepository<JumpResultToSkiJumper, Long>, QueryDslPredicateExecutor {
}

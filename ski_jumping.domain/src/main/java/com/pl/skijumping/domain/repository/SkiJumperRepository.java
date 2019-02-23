package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.SkiJumper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkiJumperRepository extends JpaRepository<SkiJumper, Long> {

    List<SkiJumper> findAll();

    List<SkiJumper> findAllByName(String name);

    SkiJumper findOneByName(String name);

    void deleteAll();
}
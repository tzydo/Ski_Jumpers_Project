package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.JumpCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JumpCategoryRepository extends JpaRepository<JumpCategory, Integer> {

    JumpCategory findById(Integer categoryId);

    JumpCategory findByShortName(String shortName);

    JumpCategory findByName(String name);
}

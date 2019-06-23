package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.Place;
import com.pl.skijumping.domain.model.HillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer>, QueryDslPredicateExecutor {
    Place findByCityAndAndHillType(String city, HillType hillType);
}

package com.pl.skijumping.domain.repository;

import com.pl.skijumping.domain.entity.DataRaceToPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRaceToPlaceRepository extends JpaRepository<DataRaceToPlace, Long> {
}

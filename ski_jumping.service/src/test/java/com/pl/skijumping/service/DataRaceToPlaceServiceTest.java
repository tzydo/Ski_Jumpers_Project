package com.pl.skijumping.service;

import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.dto.PlaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class DataRaceToPlaceServiceTest {

    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private DataRaceToPlaceService dataRaceToPlaceService;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void findByDataRaceToPlace() {
        DataRaceDTO dataRaceDTO = dataRaceService.save(new DataRaceDTO().raceId(1L).date(LocalDate.of(2018, 1, 1)));
        DataRaceDTO secondDataRaceDTO = dataRaceService.save(new DataRaceDTO().raceId(2L).date(LocalDate.of(2018, 1, 1)));
        DataRaceDTO thirdDataRaceDTO = dataRaceService.save(new DataRaceDTO().raceId(3L).date(LocalDate.of(2018, 1, 1)));

        PlaceDTO placeDTO = placeService.save(new PlaceDTO().city("test"));
        PlaceDTO secondPlaceDTO = placeService.save(new PlaceDTO().city("test2"));
        PlaceDTO thirdPlaceDTO = placeService.save(new PlaceDTO().city("test3"));

        entityManager.clear();
        entityManager.flush();
        dataRaceToPlaceService.save(new DataRaceToPlaceDTO().dataRaceId(dataRaceDTO.getId()).placeId(placeDTO.getId()));
        dataRaceToPlaceService.save(new DataRaceToPlaceDTO().dataRaceId(secondDataRaceDTO.getId()).placeId(secondPlaceDTO.getId()));
        DataRaceToPlaceDTO dataRaceToPlaceDTO = dataRaceToPlaceService.save(new DataRaceToPlaceDTO().dataRaceId(thirdDataRaceDTO.getId()).placeId(thirdPlaceDTO.getId()));

        Optional<DataRaceToPlaceDTO> actual = dataRaceToPlaceService.findByDataRaceToPlace(dataRaceToPlaceDTO);
        Assertions.assertThat(actual.isPresent()).isTrue();
        Assertions.assertThat(actual.get()).isEqualToComparingFieldByFieldRecursively(dataRaceToPlaceDTO);
    }
}
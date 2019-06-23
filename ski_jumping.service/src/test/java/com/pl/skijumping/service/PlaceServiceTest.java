package com.pl.skijumping.service;

import com.pl.skijumping.domain.model.HillType;
import com.pl.skijumping.domain.repository.PlaceRepository;
import com.pl.skijumping.dto.CountryDTO;
import com.pl.skijumping.dto.PlaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;
    @Autowired
    private CountryService countryService;

    @Test
    @Transactional
    public void findByCityAndHillType() {
        CountryDTO countryDTO = countryService.save(new CountryDTO().name("TT").shortName("test"));
        PlaceDTO placeDTO = placeService.save(
                new PlaceDTO()
                        .city("city")
                        .country(countryDTO.getId())
                        .hillSize(100)
                        .hillType(HillType.LARGE_HILL.getHillType())
        );

        placeService.save(
                new PlaceDTO()
                        .city("city2")
                        .country(countryDTO.getId())
                        .hillSize(1000)
                        .hillType(HillType.LARGE_HILL.getHillType())
        );

        placeService.save(
                new PlaceDTO()
                        .city("city3")
                        .country(countryDTO.getId())
                        .hillSize(10300)
                        .hillType(HillType.LARGE_HILL.getHillType())
        );
        Optional<PlaceDTO> foundPlace = placeService.findByCityAndHillType(placeDTO);
        Assertions.assertThat(foundPlace.isPresent()).isTrue();
        Assertions.assertThat(foundPlace.get()).isEqualToComparingFieldByFieldRecursively(placeDTO);
    }
}
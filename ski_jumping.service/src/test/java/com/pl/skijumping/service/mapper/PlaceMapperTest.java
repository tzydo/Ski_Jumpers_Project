package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.Country;
import com.pl.skijumping.domain.entity.Place;
import com.pl.skijumping.domain.model.HillType;
import com.pl.skijumping.domain.repository.CountryRepository;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class PlaceMapperTest {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PlaceMapper placeMapper;

    @Test
    public void testPlaceMapperToDTO() {
        Place place = new Place().id(1).country(new Country().id(1)).city("city").hillSize(11).hillType(HillType.LARGE_HILL);
        PlaceDTO expectedPlaceDTO = new PlaceDTO().id(1).country(1).city("city").hillSize(11).hillType(HillType.LARGE_HILL.getHillType());
        Assertions.assertThat(placeMapper.toDTO(place)).isEqualToComparingFieldByField(expectedPlaceDTO);
    }

    @Test
    @Transactional
    public void testPlaceMapperFromDTO() {
        Country country = countryRepository.save(new Country().name("nn").shortName("nm"));
        Place expectedPlace = new Place().id(1).country(country).city("city").hillSize(11).hillType(HillType.LARGE_HILL);
        PlaceDTO placeDTO = new PlaceDTO().id(1).country(country.getId()).city("city").hillSize(11).hillType(HillType.LARGE_HILL.getHillType());
        Assertions.assertThat(placeMapper.fromDTO(placeDTO)).isEqualToIgnoringGivenFields(expectedPlace, "id");
    }
}
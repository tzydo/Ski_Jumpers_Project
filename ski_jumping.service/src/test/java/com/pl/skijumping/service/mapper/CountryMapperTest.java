package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.Country;
import com.pl.skijumping.dto.CountryDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryMapperTest {

    @Test
    public void testCountryMapperToDTO() {
        Country country = new Country().id(1).name("name").shortName("shortName");
        CountryDTO expectedCountryDTO = new CountryDTO().id(1).name("name").shortName("shortName");
        Assertions.assertThat(CountryMapper.COUNTRY_MAPPER.toDTO(country)).isEqualToComparingFieldByField(expectedCountryDTO);
    }

    @Test
    public void testCountryMapperFromDTO() {
        CountryDTO countryDTO = new CountryDTO().id(1).name("name").shortName("shortName");
        Country expectedCountry = new Country().id(1).name("name").shortName("shortName");
        Assertions.assertThat(CountryMapper.COUNTRY_MAPPER.fromDTO(countryDTO)).isEqualToComparingFieldByField(expectedCountry);
    }
}
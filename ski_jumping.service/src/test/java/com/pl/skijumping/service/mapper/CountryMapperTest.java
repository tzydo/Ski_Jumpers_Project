package com.pl.skijumping.domain.mapper;

import com.pl.skijumping.domain.SkiJumpingApplication;
import com.pl.skijumping.domain.dto.CountryDTO;
import com.pl.skijumping.domain.entity.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SkiJumpingApplication.class)
@ActiveProfiles("test")
public class CountryMapperTest {

    private static final int FIRST_COUNTRY = 1;
    private static final int SECOND_COUNTRY = 2;
    private static final int THIRD_COUNTRY = 3;
    private static final String FIRST_COUNTRY_NAME = "name";
    private static final String SECOND_COUNTRY_NAME = "name2";
    private static final String THIRD_SECOND_COUNTRY_NAME = "name3";

    @Autowired
    private CountryMapper countryMapper;

    @Test
    public void toDTOTest() {
        Country firstCountry = new Country(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        CountryDTO firstCountryDTO = new CountryDTO(FIRST_COUNTRY, FIRST_COUNTRY_NAME);

        assertThat(countryMapper.toDTO(firstCountry)).isEqualTo(firstCountryDTO);
    }

    @Test
    public void toDTOListTest() {
        CountryDTO firstCountryDTO = new CountryDTO(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        CountryDTO secondCountryDTO = new CountryDTO(SECOND_COUNTRY, SECOND_COUNTRY_NAME);
        CountryDTO thirdCountryDTO = new CountryDTO(THIRD_COUNTRY, THIRD_SECOND_COUNTRY_NAME);
        List<CountryDTO> expectedCountryDTOList = Arrays
                .asList(firstCountryDTO, secondCountryDTO, thirdCountryDTO);


        Country firstCountry = new Country(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        Country secondCountry = new Country(SECOND_COUNTRY, SECOND_COUNTRY_NAME);
        Country thirdCountry = new Country(THIRD_COUNTRY, THIRD_SECOND_COUNTRY_NAME);
        List<CountryDTO> actualCountryDTOList = countryMapper
                .toDTO(Arrays.asList(firstCountry, secondCountry, thirdCountry));

        assertThat(actualCountryDTOList).isEqualTo(expectedCountryDTOList);
    }

    @Test
    public void fromDTOTest() {
        CountryDTO firstCountryDTO = new CountryDTO(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        Country firstCountry = new Country(FIRST_COUNTRY, FIRST_COUNTRY_NAME);

        assertThat(countryMapper.fromDTO(firstCountryDTO)).isEqualTo(firstCountry);
    }

    @Test
    public void fromDTOListTest() {
        Country firstCountry = new Country(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        Country secondCountry = new Country(SECOND_COUNTRY, SECOND_COUNTRY_NAME);
        Country thirdCountry = new Country(THIRD_COUNTRY, THIRD_SECOND_COUNTRY_NAME);
        List<Country> expectedCountryList = Arrays
                .asList(firstCountry, secondCountry, thirdCountry);

        CountryDTO firstCountryDTO = new CountryDTO(FIRST_COUNTRY, FIRST_COUNTRY_NAME);
        CountryDTO secondCountryDTO = new CountryDTO(SECOND_COUNTRY, SECOND_COUNTRY_NAME);
        CountryDTO thirdCountryDTO = new CountryDTO(THIRD_COUNTRY, THIRD_SECOND_COUNTRY_NAME);
        List<Country> actualCountryList = countryMapper
                .fromDTO(Arrays.asList(firstCountryDTO, secondCountryDTO, thirdCountryDTO));

        assertThat(actualCountryList).isEqualTo(expectedCountryList);
    }
}
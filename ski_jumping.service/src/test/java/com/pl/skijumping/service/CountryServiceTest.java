//package com.pl.skijumping.service;
//
//import com.pl.skijumping.domain.SkiJumpingApplication;
//import com.pl.skijumping.domain.entity.Country;
//import com.pl.skijumping.domain.repository.CountryRepository;
//import com.pl.skijumping.dto.CountryDTO;
//import com.pl.skijumping.service.mapper.CountryMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import org.springframework.transaction.annotation.Transactional;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.junit.Assert.assertArrayEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = SkiJumpingApplication.class)
//@ActiveProfiles("test")
//@DataJpaTest
//public class CountryServiceTest {
//
//    private static final String DIFFERENT_ORDER_IN_LIST = "different order in list!";
//    private static final String FRANCE = "France";
//    private static final String ENGLAND = "England";
//    private static final String POLAND = "Poland";
//
//    @Autowired
//    private CountryRepository countryRepository;
//
//    @Autowired
//    private CountryMapper countryMapper;
//
//    @Test
//    @Transactional
//    public void getCountriesTest() {
//        List<Country> countryList = Arrays.asList(
//                Country.builder().name(POLAND).build(),
//                Country.builder().name(ENGLAND).build(),
//                Country.builder().name(FRANCE).build());
//
//        countryRepository.save(countryList);
//        CountryService countryService = new CountryService(countryRepository, countryMapper);
//        Optional<List<CountryDTO>> expectedCountryDTOList = countryService.getCountries();
//
//        assertThat(expectedCountryDTOList.isPresent()).isTrue();
//        assertThat(expectedCountryDTOList.get()).isNotEmpty();
//        assertArrayEquals(DIFFERENT_ORDER_IN_LIST,
//                countryMapper.toDTO(countryList).toArray(),
//                (expectedCountryDTOList.get().toArray()));
//    }
//
//    @Test
//    public void getCountriesWhenEmptyDBTest() {
//        CountryService countryService = new CountryService(countryRepository, countryMapper);
//        Optional<List<CountryDTO>> expectedCountryDTOList = countryService.getCountries();
//
//        assertThat(expectedCountryDTOList.isPresent()).isFalse();
//    }
//
////    public void getCountriesByPattern() throws Exception {
////    }
//}
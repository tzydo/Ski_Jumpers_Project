package com.pl.skijumping.service;

import com.pl.skijumping.dto.CountryDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@ActiveProfiles("test")
@DataJpaTest
public class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Test
    @Transactional
    public void saveTest() {
        CountryDTO countryDTO = new CountryDTO().shortName("test").name("fullTest");
        countryService.save(countryDTO);
        Assertions.assertThat(countryService.findAll()).usingElementComparatorIgnoringFields("id").contains(countryDTO).hasSize(1);
    }

    @Test
    @Transactional
    public void findByShortNameTest() {
        countryService.save(new CountryDTO().shortName("test").name("fullTest"));
        countryService.save(new CountryDTO().shortName("test2").name("fullTest2"));
        CountryDTO countryDTO = countryService.save(new CountryDTO().shortName("test3").name("fullTest3"));
        Assertions.assertThat(countryService.findByShortName("test3").get()).isEqualToComparingFieldByFieldRecursively(countryDTO);
    }
}
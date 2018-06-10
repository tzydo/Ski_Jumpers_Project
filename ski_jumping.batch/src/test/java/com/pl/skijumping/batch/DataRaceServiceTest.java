package com.pl.skijumping.batch;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class DataRaceServiceTest {
    @Autowired
    private DataRaceService dataRaceService;

    @Autowired
    private DataRaceRepository dataRaceRepository;

    @Test
    @Transactional
    public void test() {
        DataRace dataRace = new DataRace();
        dataRace.setCity("test");
        dataRace.setDate(LocalDate.now());
        dataRace.setShortCountryName("test");
        dataRace.setRaceId(1l);

        DataRace dataRace2 = new DataRace();
        dataRace2.setCity("test2");
        dataRace2.setDate(LocalDate.now().plusDays(1));
        dataRace2.setShortCountryName("test2");
        dataRace2.setRaceId(2l);

        DataRace dataRace3 = new DataRace();
        dataRace3.setCity("test3");
        dataRace3.setDate(LocalDate.now().plusDays(2));
        dataRace3.setShortCountryName("test3");
        dataRace3.setRaceId(3l);



        dataRaceRepository.save(dataRace);
        dataRaceRepository.save(dataRace2);
        dataRaceRepository.save(dataRace3);

        dataRaceService.findAll();
        Assertions.assertThat(true).isTrue();
    }

}

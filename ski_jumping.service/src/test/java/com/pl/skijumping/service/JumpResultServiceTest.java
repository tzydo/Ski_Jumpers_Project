package com.pl.skijumping.service;

import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class JumpResultServiceTest {

    @Autowired
    private SkiJumperService skiJumperService;
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private JumpResultService jumpResultService;

    @Test
    @Transactional
    public void findByParamsTest() {
        DataRaceDTO dataRaceDTO = dataRaceService.save(new DataRaceDTO().date(LocalDate.now()).shortCountryName("name").city("city").raceId(1L));
        DataRaceDTO secondDataRaceDTO = dataRaceService.save(new DataRaceDTO().date(LocalDate.now()).shortCountryName("name2").city("city2").raceId(2L));
        SkiJumperDTO skiJumperDTO = skiJumperService.save(new SkiJumperDTO().name("test"));
        SkiJumperDTO secondSkiJumperDTO = skiJumperService.save(new SkiJumperDTO().name("test2"));
        JumpResultDTO expectedJumpResultDTO = jumpResultService.save(new JumpResultDTO().rank(1).dataRaceId(dataRaceDTO.getRaceId()).jumperId(skiJumperDTO.getId()).totalPoints(200.0));
        jumpResultService.save(new JumpResultDTO().rank(2).dataRaceId(secondDataRaceDTO.getRaceId()).jumperId(secondSkiJumperDTO.getId()).totalPoints(200.0));

        Optional<JumpResultDTO> actualJumpResultDTO = jumpResultService.findByParams(dataRaceDTO.getRaceId(), skiJumperDTO.getId(), 1);
        Assertions.assertThat(actualJumpResultDTO.isPresent()).isTrue();
        Assertions.assertThat(actualJumpResultDTO.get()).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}
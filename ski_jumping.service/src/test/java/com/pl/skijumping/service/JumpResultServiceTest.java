package com.pl.skijumping.service;

import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class JumpResultServiceTest {

    @Autowired
    private JumpResultToDataRaceService jumpResultToDataRaceService;
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private JumpResultService jumpResultService;
    @Autowired
    private SkiJumperService skiJumperService;

    @Test
    @Transactional
    public void testFindByRankAndDataRace() {
        Integer searchRank = 1;
        Integer secondRank = 2;
        Long raceId = 1L;
        Long secondRaceId = 2L;

        SkiJumperDTO skiJumperDTO = skiJumperService.save(new SkiJumperDTO().name("test"));

        JumpResultDTO jumpResultDTO = jumpResultService.save(new JumpResultDTO().rank(searchRank).jumperId(skiJumperDTO.getId()));
        JumpResultDTO secondJumpResultDTO = jumpResultService.save(new JumpResultDTO().rank(searchRank).jumperId(skiJumperDTO.getId()));

        DataRaceDTO dataRaceDTO = dataRaceService.save(new DataRaceDTO().raceId(raceId).date(LocalDate.now()).city("city").shortCountryName("name"));
        DataRaceDTO secondDataRaceDTO = dataRaceService.save(new DataRaceDTO().raceId(secondRaceId).date(LocalDate.now()).city("city2").shortCountryName("name2"));

        JumpResultToDataRaceDTO jumpResultToDataRaceDTO = jumpResultToDataRaceService.save(
                new JumpResultToDataRaceDTO().jumpResultId(jumpResultDTO.getId()).dataRaceId(dataRaceDTO.getRaceId()));
        JumpResultToDataRaceDTO secondJumpResultToDataRaceDTO = jumpResultToDataRaceService.save(
                new JumpResultToDataRaceDTO().jumpResultId(secondJumpResultDTO.getId()).dataRaceId(secondDataRaceDTO.getRaceId()));

        jumpResultDTO.setJumpResultToDataRaceId(jumpResultToDataRaceDTO.getId());
        secondJumpResultDTO.setJumpResultToDataRaceId(secondJumpResultToDataRaceDTO.getId());

        jumpResultDTO = jumpResultService.save(jumpResultDTO);
        secondJumpResultDTO = jumpResultService.save(secondJumpResultDTO);

        Optional<JumpResultDTO> actualJumpResult = jumpResultService.findByRankAndDataRace(searchRank, raceId);
        Assertions.assertThat(actualJumpResult.isPresent()).isTrue();

        Assertions.assertThat(actualJumpResult.get()).isEqualToComparingFieldByFieldRecursively(jumpResultDTO);


    }
}
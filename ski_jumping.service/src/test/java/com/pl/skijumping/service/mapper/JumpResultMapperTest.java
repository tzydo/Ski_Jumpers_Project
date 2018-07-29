package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.entity.JumpResult;
import com.pl.skijumping.domain.entity.SkiJumper;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.domain.repository.JumpResultRepository;
import com.pl.skijumping.domain.repository.SkiJumperRepository;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.ApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class JumpResultMapperTest {

    @Autowired
    private JumpResultMapper jumpResultMapper;
    @Autowired
    private DataRaceRepository dataRaceRepository;
    @Autowired
    private JumpResultRepository jumpResultRepository;
    @Autowired
    private SkiJumperRepository skiJumperRepository;

    @Test
    @Transactional
    public void fromDTOTest() {
        SkiJumper skiJumper = skiJumperRepository.save(new SkiJumper().name("testJumper"));
        DataRace dataRace = dataRaceRepository.save(new DataRace().city("city").date(LocalDate.now()).raceId(1l).shortCountryName("cit"));
        JumpResultDTO jumpResultDTO = new JumpResultDTO()
                .jumperId(skiJumper.getId())
                .rank(1)
                .dataRaceId(dataRace.getId())
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        JumpResult expectedJumpResult = new JumpResult()
                .skiJumper(skiJumper)
                .dataRace(dataRace)
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        Assertions.assertThat(jumpResultMapper.fromDTO(jumpResultDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedJumpResult);
    }

    @Test
    public void toDTOTest() {
        SkiJumper skiJumper = skiJumperRepository.save(new SkiJumper().name("testJumper"));
        DataRace dataRace = dataRaceRepository.save(new DataRace().city("city").date(LocalDate.now()).raceId(1l).shortCountryName("cit"));

        JumpResult jumpResult = new JumpResult()
                .skiJumper(skiJumper)
                .dataRace(dataRace)
                .rank(1)
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .jumperId(skiJumper.getId())
                .rank(1)
                .dataRaceId(dataRace.getId())
                .firstJump(100)
                .pointsForFirstJump(100)
                .secondJump(200)
                .pointsForSecondJump(200)
                .totalPoints(300);

        Assertions.assertThat(jumpResultMapper.toDTO(jumpResult))
                .isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}
package com.pl.skijumping.service;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
import com.pl.skijumping.service.mapper.DataRaceMapper;
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
public class DataRaceServiceTest {
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private DataRaceRepository dataRaceRepository;
    @Autowired
    private DataRaceMapper dataRaceMapper;
    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;
    @Autowired
    private JumpCategoryService jumpCategoryService;

    @Test
    @Transactional
    public void saveTest() {
        JumpCategoryDTO jumpCategoryDTO = jumpCategoryService.save(new JumpCategoryDTO().name("x").shortName("xx"));
        competitionTypeService.save(new CompetitionTypeDTO().type("type"));

        DataRaceDTO dataRaceDTO = dataRaceService.save(
                new DataRaceDTO()
                        .isCancelled(false)
                        .jumpCategoryId(jumpCategoryDTO.getId())
                        .codex("123")
                        .gender("M")
                        .date(LocalDate.of(2019, 1, 1))
                        .competitionType("type")
                        .raceId(123L)
                        .eventId(123L)
                        .seasonCode(2018));

        Optional<DataRaceDTO> actualDataRace = dataRaceService.findById(dataRaceDTO.getId());
        Assertions.assertThat(actualDataRace.isPresent()).isTrue();
        Assertions.assertThat(actualDataRace.get()).isEqualToComparingFieldByFieldRecursively(dataRaceDTO);
    }

    @Test
    @Transactional
    public void findByDataRaceIdTest() {
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(new CompetitionTypeDTO(null, "type", null));

        DataRace dataRace = dataRaceRepository.save(
                new DataRace()
                        .date(LocalDate.now())
                        .competitionType(competitionTypeMapper.fromDTO(competitionTypeDTO))
                        .raceId(1L)
        );

        dataRaceRepository.save(
                new DataRace()
                        .date(LocalDate.now())
                        .competitionType(competitionTypeMapper.fromDTO(competitionTypeDTO))
                        .raceId(2L)
        );

        dataRaceRepository.save(
                new DataRace()
                        .date(LocalDate.now())
                        .competitionType(competitionTypeMapper.fromDTO(competitionTypeDTO))
                        .raceId(3L)
        );

        DataRaceService dataRaceService = new DataRaceService(dataRaceRepository, dataRaceMapper);
        Optional<DataRaceDTO> actualDataRace = dataRaceService.findByRaceId(dataRace.getRaceId());
        Assertions.assertThat(actualDataRace.isPresent()).isTrue();
        Assertions.assertThat(actualDataRace.get()).isEqualToComparingFieldByFieldRecursively(dataRaceMapper.toDTO(dataRace));
    }

    @Test
    @Transactional
    public void findByDataRaceWhenDoesNotExistTest() {
        dataRaceRepository.save(
                new DataRace()
                        .date(LocalDate.now())
                        .competitionType(null)
                        .raceId(1L)
        );

        DataRaceService dataRaceService = new DataRaceService(dataRaceRepository, dataRaceMapper);
        Optional<DataRaceDTO> actualDataRace = dataRaceService.findByRaceId(999L);
        Assertions.assertThat(actualDataRace.isPresent()).isFalse();
        Assertions.assertThat(actualDataRace).isEqualTo(Optional.empty());
    }
}

package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.CompetitionName;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class CompetitionNameMapperTest {
    @Autowired
    private CompetitionNameMapper competitionNameMapper;
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private DataRaceMapper dataRaceMapper;

    @Test
    @Transactional
    public void fromDTOTest() {
        DataRaceDTO dataRaceDTO = dataRaceService.save(new DataRaceDTO().date(LocalDate.now()).city("city").raceId(1L).shortCountryName("short"));

        CompetitionNameDTO competitionNameDTO = new CompetitionNameDTO().id(1L)
                .name("name")
                .dataRaceList(Arrays.asList(dataRaceDTO.getId()));

        DataRace dataRace = dataRaceMapper.fromDTO(dataRaceDTO);
        CompetitionName expectedCompetitionName = new CompetitionName().id(1L).name("name");
        expectedCompetitionName.addDataRace(dataRace);

        Assertions.assertThat(competitionNameMapper.fromDTO(competitionNameDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionName);
    }

    @Test
    public void fromDTOListTest() {
        List<CompetitionName> expectedCompetitionNameList = Arrays.asList(
                new CompetitionName().name("name").id(1L),
                new CompetitionName().name("name2").id(2L),
                new CompetitionName().name("name3").id(3L)
        );

        List<CompetitionNameDTO> competitionNameDTOList = Arrays.asList(
                new CompetitionNameDTO().name("name").id(1L),
                new CompetitionNameDTO().name("name2").id(2L),
                new CompetitionNameDTO().name("name3").id(3L)
        );

        Assertions.assertThat(competitionNameMapper.fromDTO(competitionNameDTOList)).containsAll(expectedCompetitionNameList);
    }

    @Test
    public void toDTOTest() {
        CompetitionNameDTO expectedCompetitionNameDTO = new CompetitionNameDTO().name("name").id(1L);
        CompetitionName competitionName = new CompetitionName().id(1L).name("name");

        Assertions.assertThat(competitionNameMapper.toDTO(competitionName))
                .isEqualToComparingFieldByFieldRecursively(expectedCompetitionNameDTO);
    }

    @Test
    public void toDTOListTest() {
        List<CompetitionName> competitionNameList = Arrays.asList(
                new CompetitionName().name("name").id(1L),
                new CompetitionName().name("name2").id(2L),
                new CompetitionName().name("name3").id(3L)
        );

        List<CompetitionNameDTO> expectedCompetitionNameDTOList = Arrays.asList(
                new CompetitionNameDTO().name("name").id(1L),
                new CompetitionNameDTO().name("name2").id(2L),
                new CompetitionNameDTO().name("name3").id(3L)
        );

        Assertions.assertThat(competitionNameMapper.toDTO(competitionNameList)).containsAll(expectedCompetitionNameDTOList);
    }
}
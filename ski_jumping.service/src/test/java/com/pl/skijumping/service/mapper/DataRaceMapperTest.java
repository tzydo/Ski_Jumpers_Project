package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import com.pl.skijumping.service.CompetitionNameService;
import com.pl.skijumping.service.CompetitionTypeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class DataRaceMapperTest {

    @Autowired
    private DataRaceMapper dataRaceMapper;
    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionNameService competitionNameService;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;
    @Autowired
    private CompetitionNameMapper competitionNameMapper;

    @Test
    @Transactional
    public void toDTOTest() {
        LocalDate localDate = LocalDate.now();
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO().type("type"));
        CompetitionNameDTO competitionNameDTO = competitionNameService.save(
                new CompetitionNameDTO().name("name"));

        DataRace dataRace = new DataRace()
                .city("city")
                .competitionType(competitionTypeMapper.fromDTO(competitionTypeDTO))
                .competitionName(competitionNameMapper.fromDTO(competitionNameDTO))
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO()
                .city("city")
                .competitionName("name")
                .competitionType("type")
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        Assertions.assertThat(dataRaceMapper.toDTO(dataRace))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    public void toDTOWhenCompetitionTypeDoesNotExistTest() {
        LocalDate localDate = LocalDate.now();

        DataRace dataRace = new DataRace()
                .city("city")
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO()
                .city("city")
                .competitionName(null)
                .competitionType(null)
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        Assertions.assertThat(dataRaceMapper.toDTO(dataRace))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    @Transactional
    public void fromDTOTest() {
        LocalDate localDate = LocalDate.now();
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO().type("type"));
        CompetitionNameDTO competitionNameDTO = competitionNameService.save(
                new CompetitionNameDTO().name("name"));

        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .city("city")
                .competitionName("name")
                .competitionType("type")
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        DataRace expectedDataRace = new DataRace()
                .city("city")
                .competitionType(competitionTypeMapper.fromDTO(competitionTypeDTO))
                .competitionName(competitionNameMapper.fromDTO(competitionNameDTO))
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit");

        Assertions.assertThat(dataRaceMapper.fromDTO(dataRaceDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRace);
    }

    @Test
    public void fromDTOWhenNullTest() {
        LocalDate localDate = LocalDate.now();

        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .city("city")
                .date(localDate)
                .id(1L)
                .raceId(1L)
                .shortCountryName("cit");

        DataRace expectedDataRace = new DataRace()
                .city("city")
                .competitionType(null)
                .competitionName(null)
                .date(localDate)
                .id(1L)
                .raceId(1L)
                .shortCountryName("cit");

        Assertions.assertThat(dataRaceMapper.fromDTO(dataRaceDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRace);
    }
}
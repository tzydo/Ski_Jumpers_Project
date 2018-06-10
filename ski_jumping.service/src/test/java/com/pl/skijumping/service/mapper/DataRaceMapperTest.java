package com.pl.skijumping.service.mapper;

import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.ApplicationTest;
import com.pl.skijumping.service.CompetitionTypeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class DataRaceMapperTest {

    @Autowired
    private DataRaceMapper dataRaceMapper;
    @Autowired
    private CompetitionTypeService competitionTypeService;

    @Test
    @Transactional
    public void toDTO() {
        LocalDate localDate = LocalDate.now();
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO().builder().competitionType("type").competitionName("name").build());

        DataRace dataRace = new DataRace().builder()
                .city("city")
                .competitionTypeId(competitionTypeDTO.getId())
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO().builder()
                .city("city")
                .competitionName("name")
                .competitionType("type")
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        Assertions.assertThat(dataRaceMapper.toDTO(dataRace))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    public void toDTOWhenCompetitionTypeDoesNotExistTest() {
        LocalDate localDate = LocalDate.now();

        DataRace dataRace = new DataRace().builder()
                .city("city")
                .competitionTypeId(2L)
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO().builder()
                .city("city")
                .competitionName(null)
                .competitionType(null)
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        Assertions.assertThat(dataRaceMapper.toDTO(dataRace))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    @Transactional
    public void fromDTO() {
        LocalDate localDate = LocalDate.now();
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(
                new CompetitionTypeDTO().builder().competitionType("type").competitionName("name").build());


        DataRaceDTO dataRaceDTO = new DataRaceDTO().builder()
                .city("city")
                .competitionName("name")
                .competitionType("type")
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        DataRace expectedDataRace = new DataRace().builder()
                .city("city")
                .competitionTypeId(competitionTypeDTO.getId())
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        Assertions.assertThat(dataRaceMapper.fromDTO(dataRaceDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRace);
    }

    @Test
    public void fromDTOWhenNull() {
        LocalDate localDate = LocalDate.now();

        DataRaceDTO dataRaceDTO = new DataRaceDTO().builder()
                .city("city")
                .competitionName(null)
                .competitionType(null)
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        DataRace expectedDataRace = new DataRace().builder()
                .city("city")
                .competitionTypeId(null)
                .date(localDate)
                .id(1l)
                .raceId(1l)
                .shortCountryName("cit")
                .build();

        Assertions.assertThat(dataRaceMapper.fromDTO(dataRaceDTO))
                .isEqualToComparingFieldByFieldRecursively(expectedDataRace);
    }
}
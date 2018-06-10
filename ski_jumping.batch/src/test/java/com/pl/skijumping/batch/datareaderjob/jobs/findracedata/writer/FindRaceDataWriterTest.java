package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class FindRaceDataWriterTest {

    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private DataRaceService dataRaceService;
    @MockBean
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    @Transactional
    public void writeWhenDoesNotExistTest() {
        List<DataRaceDTO> dataRaceDTOList = Arrays.asList(
                new DataRaceDTO().builder().raceId(1L).date(LocalDate.now()).competitionName("name").competitionType("type")
                        .city("city").shortCountryName("sName").build(),
                new DataRaceDTO().builder().raceId(2L).date(LocalDate.now().plusDays(1)).competitionName("name").competitionType("type")
                        .city("city").shortCountryName("sName").build(),
                new DataRaceDTO().builder().raceId(3L).date(LocalDate.now().plusDays(2)).competitionName("name2").competitionType("type2")
                        .city("city").shortCountryName("sName").build(),
                new DataRaceDTO().builder().raceId(4L).date(LocalDate.now().plusDays(3)).competitionName("name2").competitionType("type2")
                        .city("city").shortCountryName("sName").build()
        );

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(competitionTypeService,
                dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(dataRaceDTOList);

        List<CompetitionTypeDTO> actualCompetitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(actualCompetitionTypeDTOList).isNotEmpty();
        Assertions.assertThat(actualCompetitionTypeDTOList).hasSize(2);
        Assertions.assertThat(
                actualCompetitionTypeDTOList.stream().map(CompetitionTypeDTO::getCompetitionName).collect(Collectors.toList()))
                .containsAll(Arrays.asList("name", "name2"));

        Assertions.assertThat(
                actualCompetitionTypeDTOList.stream().map(CompetitionTypeDTO::getCompetitionType).collect(Collectors.toList()))
                .containsAll(Arrays.asList("type", "type2"));


        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(4);
    }

    @Test
    @Transactional
    public void writeWhenExistTest() {
        competitionTypeService.save(new CompetitionTypeDTO(null, "type","name"));

        DataRaceDTO dataRaceDTO = new DataRaceDTO().builder()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName("name")
                .competitionType("type")
                .city("city")
                .shortCountryName("sName")
                .build();

        dataRaceDTO = dataRaceService.save(dataRaceDTO);
        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(competitionTypeService,
                dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(1);
        Assertions.assertThat(actualDataRaceDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(dataRaceDTO);
    }

    @Test
    @Transactional
    public void writeWhenNullTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO().builder()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName(null)
                .competitionType("type")
                .city("city")
                .shortCountryName("sName")
                .build();

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(competitionTypeService,
                dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isEmpty();
    }

    @Test
    @Transactional
    public void writeWhenCompetitionTypeNullTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO().builder()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName("name")
                .competitionType(null)
                .city("city")
                .shortCountryName("sName")
                .build();

        dataRaceService.save(dataRaceDTO);
        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(competitionTypeService,
                dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isEmpty();
    }
}
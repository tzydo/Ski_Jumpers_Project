package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionNameService;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.mapper.CompetitionNameMapper;
import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
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
    private CompetitionNameService competitionNameService;
    @Autowired
    private DataRaceService dataRaceService;
    @MockBean
    private DiagnosticMonitor diagnosticMonitor;
    @Autowired
    private CompetitionNameMapper competitionNameMapper;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Test
    @Transactional
    public void writeWhenDoesNotExistTest() {
        List<DataRaceDTO> dataRaceDTOList = Arrays.asList(
                new DataRaceDTO().raceId(1L).date(LocalDate.now()).competitionName("name").competitionType("type")
                        .city("city").shortCountryName("sName"),
                new DataRaceDTO().raceId(2L).date(LocalDate.now().plusDays(1)).competitionName("name").competitionType("type")
                        .city("city").shortCountryName("sName"),
                new DataRaceDTO().raceId(3L).date(LocalDate.now().plusDays(2)).competitionName("name2").competitionType("type2")
                        .city("city").shortCountryName("sName"),
                new DataRaceDTO().raceId(4L).date(LocalDate.now().plusDays(3)).competitionName("name2").competitionType("type2")
                        .city("city").shortCountryName("sName"));

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(dataRaceDTOList);

        List<CompetitionTypeDTO> actualCompetitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(actualCompetitionTypeDTOList).isNotEmpty();
        Assertions.assertThat(actualCompetitionTypeDTOList).hasSize(2);
        Assertions.assertThat(
                actualCompetitionTypeDTOList.stream().map(CompetitionTypeDTO::getType).collect(Collectors.toList()))
                .containsAll(Arrays.asList("type", "type2"));

        List<CompetitionNameDTO> actualCompetitionNameDTOList = competitionNameService.findAll();
        Assertions.assertThat(actualCompetitionNameDTOList).isNotEmpty();
        Assertions.assertThat(actualCompetitionNameDTOList).hasSize(2);
        Assertions.assertThat(
                actualCompetitionNameDTOList.stream().map(CompetitionNameDTO::getName).collect(Collectors.toList()))
                .containsAll(Arrays.asList("name", "name2"));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(4);
    }

    @Test
    @Transactional
    public void writeWhenExistTest() {
        competitionTypeService.save(new CompetitionTypeDTO(null, "type"));
        competitionNameService.save(new CompetitionNameDTO(null, "name"));

        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName("name")
                .competitionType("type")
                .city("city")
                .shortCountryName("sName");

        dataRaceDTO = dataRaceService.save(dataRaceDTO);
        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(1);
        Assertions.assertThat(actualDataRaceDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(dataRaceDTO);
    }

    @Test
    @Transactional
    public void writeWhenCompetitionNameNullTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName(null)
                .competitionType("type")
                .city("city")
                .shortCountryName("sName");

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();

        dataRaceDTO.setId(actualDataRaceDTOList.get(0).getId());
        Assertions.assertThat(actualDataRaceDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(dataRaceDTO);
    }

    @Test
    @Transactional
    public void writeWhenNullTest() {
        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(null);

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isEmpty();
    }

    @Test
    @Transactional
    public void writeWhenCompetitionTypeNullTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .raceId(1L)
                .date(LocalDate.now())
                .competitionName("name")
                .competitionType(null)
                .city("city")
                .shortCountryName("sName");

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(1);
        Assertions.assertThat(actualDataRaceDTOList.get(0)).isEqualToIgnoringGivenFields(dataRaceDTO, "id");
    }
}
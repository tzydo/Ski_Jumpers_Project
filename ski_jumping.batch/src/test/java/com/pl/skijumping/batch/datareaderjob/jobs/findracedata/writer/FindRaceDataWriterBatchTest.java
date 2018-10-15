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

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class FindRaceDataWriterBatchTest {

    @MockBean
    private DiagnosticMonitor diagnosticMonitor;
    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private CompetitionNameService competitionNameService;
    @Autowired
    private DataRaceService dataRaceService;
    @Autowired
    private CompetitionNameMapper competitionNameMapper;
    @Autowired
    private CompetitionTypeMapper competitionTypeMapper;

    @Test
    @Transactional
    public void writeWhenNullTest() {
        List<DataRaceDTO> expectedDataRaceDTOList = dataRaceService.findAll();
        FindRaceDataWriterBatch findRaceDataWriterBatch =
                new FindRaceDataWriterBatch(competitionTypeService, competitionNameService, dataRaceService, diagnosticMonitor, competitionNameMapper, competitionTypeMapper);
        findRaceDataWriterBatch.write(null);
        findRaceDataWriterBatch.write(new ArrayList<>());

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(expectedDataRaceDTOList).isEmpty();
        Assertions.assertThat(expectedDataRaceDTOList).containsAll(actualDataRaceDTOList);
    }

    @Test
    @Transactional
    public void writeTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .raceId(1L).date(LocalDate.now()).competitionName("name").competitionType("type")
                .city("city").shortCountryName("sName");

        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, competitionTypeMapper, competitionNameMapper, dataRaceService, diagnosticMonitor);
        findRaceDataWriter.write(Arrays.asList(dataRaceDTO));

        List<CompetitionTypeDTO> actualCompetitionTypeDTOList = competitionTypeService.findAll();
        Assertions.assertThat(actualCompetitionTypeDTOList).isNotEmpty();
        Assertions.assertThat(actualCompetitionTypeDTOList).hasSize(1);
        Assertions.assertThat(actualCompetitionTypeDTOList.get(0).getType()).isEqualTo("type");

        List<CompetitionNameDTO> actualCompetitionNameDTOList = competitionNameService.findAll();
        Assertions.assertThat(actualCompetitionNameDTOList).isNotEmpty();
        Assertions.assertThat(actualCompetitionNameDTOList).hasSize(1);
        Assertions.assertThat(actualCompetitionNameDTOList.get(0).getName()).isEqualTo("name");

        List<DataRaceDTO> actualDataRaceDTOList = dataRaceService.findAll();
        Assertions.assertThat(actualDataRaceDTOList).isNotEmpty();
        Assertions.assertThat(actualDataRaceDTOList).hasSize(1);

        dataRaceDTO.setId(actualCompetitionTypeDTOList.get(0).getId());
        Assertions.assertThat(actualDataRaceDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(dataRaceDTO);
    }
}
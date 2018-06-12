package com.pl.skijumping.service;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.domain.repository.DataRaceRepository;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.service.mapper.DataRaceMapper;
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
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ApplicationTest.class)
public class DataRaceServiceTest {
    @Autowired
    private DataRaceRepository dataRaceRepository;
    @Autowired
    private DataRaceMapper dataRaceMapper;
    @MockBean
    private DiagnosticMonitor diagnosticMonitor;
    @Autowired
    private CompetitionTypeService competitionTypeService;

    @Test
    @Transactional
    public void findByDataRaceTest() {
        CompetitionTypeDTO competitionTypeDTO = competitionTypeService.save(new CompetitionTypeDTO(null, "type", "name"));

        DataRace dataRace = dataRaceRepository.save(new DataRace(null, LocalDate.now(), "city", "pol", competitionTypeDTO.getId(), 1l));
        dataRaceRepository.save(new DataRace(null, LocalDate.now(), "city2", "pol2", competitionTypeDTO.getId(), 2l));
        dataRaceRepository.save(new DataRace(null, LocalDate.now(), "city3", "pol3", competitionTypeDTO.getId(), 3l));

        DataRaceService dataRaceService = new DataRaceService(dataRaceRepository,
                dataRaceMapper, diagnosticMonitor);
        Optional<DataRace> actualDataRace = dataRaceService.findByDataRace(dataRace);
        Assertions.assertThat(actualDataRace.isPresent()).isTrue();
        Assertions.assertThat(actualDataRace.get()).isEqualToComparingFieldByFieldRecursively(dataRace);
    }

    @Test
    public void findByDataRaceWhenDoesNotExistTest() {
        DataRace dataRace = new DataRace(null, LocalDate.now(), "city", "pol", 1l, 1l);
        DataRaceService dataRaceService = new DataRaceService(dataRaceRepository,
                dataRaceMapper, diagnosticMonitor);
        Optional<DataRace> actualDataRace = dataRaceService.findByDataRace(dataRace);
        Assertions.assertThat(actualDataRace.isPresent()).isFalse();
        Assertions.assertThat(actualDataRace).isEqualTo(Optional.empty());
    }
}

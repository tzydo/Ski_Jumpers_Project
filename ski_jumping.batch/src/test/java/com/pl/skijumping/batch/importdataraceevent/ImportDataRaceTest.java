package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.BatchApplicationTest;
import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.*;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpCategoryService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = BatchApplicationTest.class)
public class ImportDataRaceTest {

    @Autowired
    private JumpCategoryService jumpCategoryService;
    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private DataRaceService dataRaceService;

    @Bean
    public SimpleMessageListenerContainer setMessageListener() {
        return Mockito.mock(SimpleMessageListenerContainer.class);
    }

    @Test
    @Transactional
    public void importDataTest() throws IOException, InternalServiceException {
        Path filePath = Paths.get(new ClassPathResource("import_2019_43851.txt").getURI());
        Map<String, Object> map = new HashMap<>();
        map.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2019);
        map.put(MessagePropertiesConst.EVENT_ID.getValue(), "1234");

        MessageDTO messageDTO = new MessageDTO().filePath(filePath.toString()).properties(map);

        competitionTypeService.save(new CompetitionTypeDTO().type("HS109"));
        JumpCategoryDTO jumpCategoryDTO = jumpCategoryService.save(new JumpCategoryDTO().name("OPA").shortName("OPA"));

        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        ImportDataRace importDataRace = new ImportDataRace(diagnosticMonitorMock, messageDTO, jumpCategoryService, competitionTypeService, dataRaceService);
        List<DataRaceDTO> actualDataRaceDTOS = importDataRace.importData(filePath);

        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(jumpCategoryDTO.getId())
                .codex("3242")
                .gender("MALE")
                .date(LocalDate.of(2018, 2, 15))
                .competitionType("HS109")
                .raceId(5385L)
                .eventId(1234L)
                .seasonCode(2019);

        DataRaceDTO secondDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(jumpCategoryDTO.getId())
                .codex("3243")
                .gender("MALE")
                .date(LocalDate.of(2018, 2, 16))
                .competitionType("HS109")
                .raceId(5386L)
                .eventId(1234L)
                .seasonCode(2019);

        Assertions.assertThat(actualDataRaceDTOS).usingElementComparatorIgnoringFields("id").containsAll(Arrays.asList(dataRaceDTO, secondDataRaceDTO));
    }
}
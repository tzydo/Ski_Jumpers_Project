package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessageProperties;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpCategoryService;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ImportDataRaceEventListenerTest {
    private static final String IMPORT_PLACE_EVENT_QUEUE = "import_place_event_queue";
    private static final String IMPORT_JUMP_RESULT_EVENT_QUEUE = "import_jump_result_event_queue";
    private static final String EXCHANGE = "exchange";


    @Test
    public void testImportDataRaceEventListener() throws InternalServiceException, IOException {
        RabbitmqProducer mockRabbitmqProducer = Mockito.mock(RabbitmqProducer.class);
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpCategoryService mockJumpCategoryService = Mockito.mock(JumpCategoryService.class);
        CompetitionTypeService mockCompetitionTypeService = Mockito.mock(CompetitionTypeService.class);
        DataRaceService mockDataRaceService = Mockito.mock(DataRaceService.class);
        ImportDataRaceEventListener importDataRaceEventListener = new ImportDataRaceEventListener(mockRabbitmqProducer,
                IMPORT_PLACE_EVENT_QUEUE,
                IMPORT_JUMP_RESULT_EVENT_QUEUE,
                null,
                EXCHANGE,
                diagnosticMonitorMock,
                mockJumpCategoryService,
                mockCompetitionTypeService,
                mockDataRaceService);

        JumpCategoryDTO jumpCategoryDTO = new JumpCategoryDTO().id(1).name("Qualification").shortName("QUA");
        Mockito.when(mockJumpCategoryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(jumpCategoryDTO));
        Mockito.when(mockDataRaceService.findByRaceId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(mockCompetitionTypeService.findByType(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(mockDataRaceService.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());

        Map<String, Object> propeties = new HashMap<>();
        propeties.put(MessageProperties.TOURNAMENT_YEAR.getValue(), 2018);
        propeties.put(MessageProperties.EVENT_ID.getValue(), "1");
        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource("event_2018_43167.txt").getURI()).toString())
                .properties(propeties);
        importDataRaceEventListener.importDataRace(messageDTO);

        DataRaceDTO firstDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3098")
                .gender("M")
                .date(LocalDate.of(2017, 12, 29))
                .competitionType("HS137")
                .raceId(5239L)
                .eventId(1L)
                .seasonCode(2018);


        DataRaceDTO secondDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3099")
                .gender("M")
                .date(LocalDate.of(2017, 12, 30))
                .competitionType("HS137")
                .raceId(5240L)
                .eventId(1L)
                .seasonCode(2018);

        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(firstDataRaceDTO);
        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(secondDataRaceDTO);

        Mockito.verify(mockRabbitmqProducer, Mockito.times(4)).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.any());
    }
}
package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ImportDataRaceEventListenerTest {
    private static final String JUMP_RESULT_HOST = "jumpResultHost";
    private static final String IMPORT_PLACE_EVENT_QUEUE = "importPlaceEventQueue";
    private static final String IMPORT_JUMP_RESULT_EVENT_QUEUE = "importJumpResultEventQueue";
    private static final String IMPORT_JUMP_RESULT_TEAM_EVENT_QUEUE = "importJumpResultTeamEventQueue";
    private static final String SOURCE_IMPORT_EVENT_LISTENER = "sourceImportEventListener";
    private static final String EXCHANGE = "exchange";
    @Mock
    private RabbitmqProducer rabbitmqProducer;
    @Mock
    private JumpCategoryService jumpCategoryService;
    @Mock
    private CompetitionTypeService competitionTypeService;
    @Mock
    private DataRaceService dataRaceService;

    @Test
    public void testImportDataRaceEventListener() throws InternalServiceException, IOException {
        RabbitmqProducer mockRabbitmqProducer = Mockito.mock(RabbitmqProducer.class);
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpCategoryService mockJumpCategoryService = Mockito.mock(JumpCategoryService.class);
        CompetitionTypeService mockCompetitionTypeService = Mockito.mock(CompetitionTypeService.class);
        DataRaceService mockDataRaceService = Mockito.mock(DataRaceService.class);
        ImportDataRaceEventListener importDataRaceEventListener = new ImportDataRaceEventListener(mockRabbitmqProducer,
                "host",
                IMPORT_PLACE_EVENT_QUEUE,
                IMPORT_JUMP_RESULT_EVENT_QUEUE,
                IMPORT_JUMP_RESULT_TEAM_EVENT_QUEUE,
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

        Map<String, Object> properties = new HashMap<>();
        properties.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2018);
        properties.put(MessagePropertiesConst.EVENT_ID.getValue(), "1");
        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource("event_2018_43167.txt").getURI()).toString())
                .properties(properties);
        importDataRaceEventListener.importDataRace(messageDTO);

        DataRaceDTO firstDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3828")
                .gender("M")
                .date(LocalDate.of(2016, 12, 29))
                .competitionType("HS137")
                .raceId(4706L)
                .eventId(1L)
                .seasonCode(2018);

        DataRaceDTO secondDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3829")
                .gender("M")
                .date(LocalDate.of(2016, 12, 30))
                .competitionType("HS137")
                .raceId(4707L)
                .eventId(1L)
                .seasonCode(2018);

        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(firstDataRaceDTO);
        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(secondDataRaceDTO);

        Mockito.verify(mockRabbitmqProducer, Mockito.times(4)).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testImportDataRaceEventListenerSecond() throws InternalServiceException, IOException {
        RabbitmqProducer mockRabbitmqProducer = Mockito.mock(RabbitmqProducer.class);
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpCategoryService mockJumpCategoryService = Mockito.mock(JumpCategoryService.class);
        CompetitionTypeService mockCompetitionTypeService = Mockito.mock(CompetitionTypeService.class);
        DataRaceService mockDataRaceService = Mockito.mock(DataRaceService.class);
        ImportDataRaceEventListener importDataRaceEventListener = new ImportDataRaceEventListener(mockRabbitmqProducer,
                "host",
                IMPORT_PLACE_EVENT_QUEUE,
                IMPORT_JUMP_RESULT_EVENT_QUEUE,
                IMPORT_JUMP_RESULT_TEAM_EVENT_QUEUE,
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

        Map<String, Object> properties = new HashMap<>();
        properties.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2018);
        properties.put(MessagePropertiesConst.EVENT_ID.getValue(), "1");
        MessageDTO messageDTO = new MessageDTO()
                .filePath(Paths.get(new ClassPathResource("event_2017_5321.txt").getURI()).toString())
                .properties(properties);
        importDataRaceEventListener.importDataRace(messageDTO);

        DataRaceDTO firstDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3186")
                .gender("M")
                .date(LocalDate.of(2017, 2, 22))
                .competitionType("HS130")
                .raceId(5321L)
                .eventId(1L)
                .seasonCode(2018);

        DataRaceDTO secondDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3187")
                .gender("M")
                .date(LocalDate.of(2017, 2, 23))
                .competitionType("HS130")
                .raceId(5322L)
                .eventId(1L)
                .seasonCode(2018);

        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(firstDataRaceDTO);
        Mockito.verify(mockDataRaceService, Mockito.times(1)).save(secondDataRaceDTO);

        Mockito.verify(mockRabbitmqProducer, Mockito.times(16)).sendMessage(Mockito.anyString(), Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testImportDataRaceWhenCancelled() throws IOException, InternalServiceException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();

        Mockito.when(this.competitionTypeService.findByType(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(this.competitionTypeService.save(Mockito.any())).thenReturn(null);
        Mockito.when(this.dataRaceService.findByRaceId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(this.dataRaceService.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.jumpCategoryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(new JumpCategoryDTO().id(1)));

        String importDataRacePath = Paths.get(new ClassPathResource("import/import_2019_44510.txt").getURI()).toString();
        Map<String, Object> importMap = new HashMap<>();
        importMap.put(MessagePropertiesConst.EVENT_ID.getValue(), "44510");
        importMap.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2019);
        importMap.put(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=44510&seasoncode=2019");
        importMap.put(MessagePropertiesConst.DESTINATION_TARGET.getValue(), "importDataRaceEventListener");
        importMap.put(MessagePropertiesConst.FILE_NAME.getValue(), "import_2019_44510.txt");

        MessageDTO importDataRaceMessageDTO = new MessageDTO()
                .filePath(importDataRacePath)
                .properties(importMap);

        ImportDataRaceEventListener importDataRaceEventListener = new ImportDataRaceEventListener(rabbitmqProducer, JUMP_RESULT_HOST, IMPORT_PLACE_EVENT_QUEUE, IMPORT_JUMP_RESULT_TEAM_EVENT_QUEUE, IMPORT_JUMP_RESULT_EVENT_QUEUE, SOURCE_IMPORT_EVENT_LISTENER, EXCHANGE, diagnosticMonitorMock, jumpCategoryService, competitionTypeService, dataRaceService);
        importDataRaceEventListener.importDataRace(importDataRaceMessageDTO);
    }

    @Test
    public void testImportDataRaceWhenNotCancelled() throws InternalServiceException, IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();

        Mockito.when(this.competitionTypeService.findByType(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(this.competitionTypeService.save(Mockito.any())).thenReturn(null);
        Mockito.when(this.dataRaceService.findByRaceId(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(this.dataRaceService.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(this.jumpCategoryService.findByShortName(Mockito.anyString())).thenReturn(Optional.of(new JumpCategoryDTO().id(1)));

        String importDataRacePath = Paths.get(new ClassPathResource("import/import_2019_43175.txt").getURI()).toString();
        Map<String, Object> importMap = new HashMap<>();
        importMap.put(MessagePropertiesConst.EVENT_ID.getValue(), "43175");
        importMap.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2019);
        importMap.put(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=43175&seasoncode=2019");
        importMap.put(MessagePropertiesConst.DESTINATION_TARGET.getValue(), "importDataRaceEventListener");
        importMap.put(MessagePropertiesConst.FILE_NAME.getValue(), "import_2019_43175.txt");

        MessageDTO importDataRaceMessageDTO = new MessageDTO()
                .filePath(importDataRacePath)
                .properties(importMap);

        ImportDataRaceEventListener importDataRaceEventListener = new ImportDataRaceEventListener(rabbitmqProducer, JUMP_RESULT_HOST, IMPORT_PLACE_EVENT_QUEUE, IMPORT_JUMP_RESULT_TEAM_EVENT_QUEUE, IMPORT_JUMP_RESULT_EVENT_QUEUE, SOURCE_IMPORT_EVENT_LISTENER, EXCHANGE, diagnosticMonitorMock, jumpCategoryService, competitionTypeService, dataRaceService);
        importDataRaceEventListener.importDataRace(importDataRaceMessageDTO);

        DataRaceDTO dataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3113")
                .gender("M")
                .date(LocalDate.of(2019, 2, 1))
                .competitionType("HS235")
                .raceId(5254L)
                .eventId(43175L)
                .seasonCode(2019);

        DataRaceDTO secondDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3114")
                .gender("M")
                .date(LocalDate.of(2019, 2, 2))
                .competitionType("HS235")
                .raceId(5255L)
                .eventId(43175L)
                .seasonCode(2019);

        DataRaceDTO thirdDataRaceDTO = new DataRaceDTO()
                .isCancelled(false)
                .jumpCategoryId(1)
                .codex("3115")
                .gender("M")
                .date(LocalDate.of(2019, 2, 3))
                .competitionType("HS235")
                .raceId(5256L)
                .eventId(43175L)
                .seasonCode(2019);

        Mockito.verify(this.dataRaceService, Mockito.times(1)).save(dataRaceDTO);
        Mockito.verify(this.dataRaceService, Mockito.times(1)).save(secondDataRaceDTO);
        Mockito.verify(this.dataRaceService, Mockito.times(1)).save(thirdDataRaceDTO);
    }
}
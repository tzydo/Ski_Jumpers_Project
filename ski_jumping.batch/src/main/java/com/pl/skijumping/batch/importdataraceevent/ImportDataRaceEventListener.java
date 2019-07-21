package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpCategoryService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Component
public class ImportDataRaceEventListener {

    public static final String TEAM_PATTERN = "Team";
    private final RabbitmqProducer rabbitmqProducer;
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpCategoryService jumpCategoryService;
    private final CompetitionTypeService competitionTypeService;
    private final DataRaceService dataRaceService;
    private final String jumpResultHost;
    private final String exchange;
    private final String importPlaceEventQueue;
    private final String importJumpResultEventQueue;
    private final String importJumpResultTeamEventQueue;
    private final String sourceImportEventListener;

    public ImportDataRaceEventListener(RabbitmqProducer rabbitmqProducer,
                                       @Value("${skijumping.settings.jumpResultHost}") String jumpResultHost,
                                       @Value("${skijumping.rabbitmq.queues.importPlaceEventListener}") String importPlaceEventQueue,
                                       @Value("${skijumping.rabbitmq.queues.importJumpResultEventListener}") String importJumpResultEventQueue,
                                       @Value("${skijumping.rabbitmq.queues.importJumpResultTeamEventListener}") String importJumpResultTeamEventQueue,
                                       @Value("${skijumping.rabbitmq.queues.sourceImportEventListener}") String sourceImportEventListener,
                                       @Value("${skijumping.rabbitmq.exchange}") String exchange,
                                       DiagnosticMonitor diagnosticMonitor,
                                       JumpCategoryService jumpCategoryService,
                                       CompetitionTypeService competitionTypeService,
                                       DataRaceService dataRaceService) {
        this.rabbitmqProducer = rabbitmqProducer;
        this.jumpResultHost = jumpResultHost;
        this.importPlaceEventQueue = importPlaceEventQueue;
        this.importJumpResultEventQueue = importJumpResultEventQueue;
        this.importJumpResultTeamEventQueue = importJumpResultTeamEventQueue;
        this.sourceImportEventListener = sourceImportEventListener;
        this.exchange = exchange;
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpCategoryService = jumpCategoryService;
        this.competitionTypeService = competitionTypeService;
        this.dataRaceService = dataRaceService;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importDataRaceEventListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true"),
            key = "importDataRaceEventListener"
    ))
    public void importDataRace(MessageDTO messageDTO) throws InternalServiceException {
        if (messageDTO == null) {
            return;
        }

        Path filePath = Paths.get(messageDTO.getFilePath());
        if (!filePath.toFile().exists()) {
            throw new InternalServiceException("Cannot get not existing file from path: " + filePath);
        }

        ImportDataRace importDataRace = new ImportDataRace(diagnosticMonitor, messageDTO, jumpCategoryService, competitionTypeService, dataRaceService);
        List<DataRaceDTO> dataRaceDTOS = importDataRace.importData(filePath);
        if (dataRaceDTOS.isEmpty()) {
            diagnosticMonitor.logInfo("Not found data race template, removing file : " + filePath.getFileName().toString());
            filePath.toFile().deleteOnExit();
            return;
        }

        dataRaceDTOS.stream()
                .filter(Objects::nonNull)
                .filter(race -> !race.getIsCancelled())
                .forEach(raceDTO -> {
                    importPlaceEvent(raceDTO, messageDTO.getFilePath());
                    if (raceDTO.getCompetitionType().contains(TEAM_PATTERN)) {
                        importTeamJumpResultData(raceDTO, messageDTO.getFilePath());
                    } else {
                        importJumpResultData(raceDTO, messageDTO.getFilePath());
                    }
                });
    }

    private void importPlaceEvent(DataRaceDTO dataRaceDTO, String filePath) {
        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .addProperties(MessagePropertiesConst.DARA_RACE_ID.getValue(), dataRaceDTO.getId())
                .addProperties(MessagePropertiesConst.COMPETITION_TYPE.getValue(), dataRaceDTO.getCompetitionType());
        rabbitmqProducer.sendMessage(exchange, importPlaceEventQueue, messageDTO);
    }

    private void importJumpResultData(DataRaceDTO dataRaceDTO, String filePath) {
        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .addProperties(MessagePropertiesConst.DARA_RACE_ID.getValue(), dataRaceDTO.getId())
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), String.format(jumpResultHost, dataRaceDTO.getRaceId()))
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), importJumpResultEventQueue)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), FileScannerConst.prepareFileName(FileScannerConst.FILE_JUMP_RESULT, dataRaceDTO.getRaceId().toString()));
        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }

    private void importTeamJumpResultData(DataRaceDTO dataRaceDTO, String filePath) {
        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .addProperties(MessagePropertiesConst.DARA_RACE_ID.getValue(), dataRaceDTO.getId())
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), String.format(jumpResultHost, dataRaceDTO.getRaceId()))
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), importJumpResultTeamEventQueue)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), FileScannerConst.prepareFileName(FileScannerConst.FILE_JUMP_RESULT, dataRaceDTO.getRaceId().toString()));
        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }
}

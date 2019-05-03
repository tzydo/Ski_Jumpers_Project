package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessageProperties;
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

@Component
public class ImportDataRaceEventListener {

    private final RabbitmqProducer rabbitmqProducer;
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpCategoryService jumpCategoryService;
    private final CompetitionTypeService competitionTypeService;
    private final DataRaceService dataRaceService;
    private final String exchange;
    private final String importPlaceEventQueue;
    private final String importJumpResultEventQueue;
    private final String sourceImportEventListener;

    public ImportDataRaceEventListener(RabbitmqProducer rabbitmqProducer,
                                       @Value("${skijumping.rabbitmq.queues.importPlaceEventListener}") String importPlaceEventQueue,
                                       @Value("${skijumping.rabbitmq.queues.importJumpResultEventListener}") String importJumpResultEventQueue,
                                       @Value("${skijumping.rabbitmq.queues.sourceImportEventListener}") String sourceImportEventListener,
                                       @Value("${skijumping.rabbitmq.exchange}") String exchange,
                                       DiagnosticMonitor diagnosticMonitor,
                                       JumpCategoryService jumpCategoryService,
                                       CompetitionTypeService competitionTypeService,
                                       DataRaceService dataRaceService) {
        this.rabbitmqProducer = rabbitmqProducer;
        this.importPlaceEventQueue = importPlaceEventQueue;
        this.importJumpResultEventQueue = importJumpResultEventQueue;
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
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true")
    ))
    public void importDataRace(MessageDTO messageDTO) throws InternalServiceException {
        if (messageDTO == null) {
            return;
        }

        Path filePath = Paths.get(messageDTO.getFilePath());
        if (!filePath.toFile().exists()) {
            throw new InternalServiceException("Cannot get not existing file from path: " + filePath);
        }

        ImportDataRace importDataRace = new ImportDataRace(diagnosticMonitor, messageDTO.getProperties(), jumpCategoryService, competitionTypeService, dataRaceService);
        List<DataRaceDTO> dataRaceDTOS = importDataRace.importData(filePath);
        dataRaceDTOS.forEach(raceDTO -> {
            importPlaceEvent(raceDTO, messageDTO.getFilePath());
            importJumpResultData(raceDTO, messageDTO.getFilePath());
        });
    }

    private void importPlaceEvent(DataRaceDTO dataRaceDTO, String filePath) {
        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .addProperties(MessageProperties.DARA_RACE_ID.getValue(), dataRaceDTO.getRaceId())
                .addProperties(MessageProperties.COMPETITION_TYPE.getValue(), dataRaceDTO.getCompetitionType());
        rabbitmqProducer.sendMessage(exchange, importPlaceEventQueue, messageDTO);
    }

    private void importJumpResultData(DataRaceDTO dataRaceDTO, String filePath) {
        MessageDTO messageDTO = new MessageDTO()
                .filePath(filePath)
                .addProperties(MessageProperties.DARA_RACE_ID.getValue(), dataRaceDTO.getRaceId())
                .addProperties(MessageProperties.DESTINATION_TARGET.getValue(), importJumpResultEventQueue)
                .addProperties(MessageProperties.FILE_NAME.getValue(), FileScannerConst.prepapeFileName(FileScannerConst.FILE_JUMP_RESULT, "_" + MessageProperties.DARA_RACE_ID.getValue()));
        //todo add property url
        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }
}

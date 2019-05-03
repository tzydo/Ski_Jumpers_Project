package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessageProperties;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;


@Component
public class ImportJumpResultDataListener {

    private final JumpResultService jumpResultService;
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpResultToDataRaceService jumpResultToDataRaceService;

    public ImportJumpResultDataListener(JumpResultService jumpResultService,
                                        DiagnosticMonitor diagnosticMonitor,
                                        JumpResultToDataRaceService jumpResultToDataRaceService) {
        this.jumpResultService = jumpResultService;
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpResultToDataRaceService = jumpResultToDataRaceService;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importJumpResultDataListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true")
    ))
    public void importData(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return;
        }

        String filePath = messageDTO.getFilePath();
        Long dataRaceId = (Long) messageDTO.getProperties().get(MessageProperties.DARA_RACE_ID.getValue());
        ImportJumpResultData importJumpResultData = new ImportJumpResultData(diagnosticMonitor, jumpResultService);
        Set<JumpResultDTO> jumpResultDTOSet = importJumpResultData.importData(filePath);
        jumpResultDTOSet.stream()
                .filter(Objects::nonNull)
                .forEach(jumpResultDTO -> {
                    jumpResultToDataRaceService.save(new JumpResultToDataRaceDTO().dataRaceId(dataRaceId).jumpResultId(jumpResultDTO.getId()));
                    sendMessage(jumpResultDTO, dataRaceId);
                });
    }

    private void sendMessage(JumpResultDTO jumpResult, Long dataRace) {
        //toDo message to import jumper
    }
}
package com.pl.skijumping.batch.importjumpresultdataevent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;


@Component
public class ImportJumpResultDataListener {

    private final JumpResultService jumpResultService;
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpResultToDataRaceService jumpResultToDataRaceService;
    private final RabbitmqProducer rabbitmqProducer;
    private final String importSkiJumperDataListener;
    private final String exchange;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ImportJumpResultDataListener(JumpResultService jumpResultService,
                                        DiagnosticMonitor diagnosticMonitor,
                                        JumpResultToDataRaceService jumpResultToDataRaceService,
                                        RabbitmqProducer rabbitmqProducer,
                                        @Value("${skijumping.rabbitmq.queues.importSkiJumperDataListener}") String importSkiJumperDataListener,
                                        @Value("${skijumping.rabbitmq.exchange}") String exchange) {
        this.jumpResultService = jumpResultService;
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpResultToDataRaceService = jumpResultToDataRaceService;
        this.rabbitmqProducer = rabbitmqProducer;
        this.importSkiJumperDataListener = importSkiJumperDataListener;
        this.exchange = exchange;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importJumpResultEventListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.DIRECT, durable = "true"),
            key = "importJumpResultEventListener"
    ))
    public void importData(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return;
        }

        Long dataRaceId = messageDTO.getProperties().getLongValue(MessagePropertiesConst.DARA_RACE_ID.getValue());
        ImportJumpResultData importJumpResultData = new ImportJumpResultData(diagnosticMonitor, jumpResultService);
        Set<JumpResultDTO> jumpResultDTOSet = importJumpResultData.importData(messageDTO.getFilePath());
        jumpResultDTOSet.stream()
                .filter(Objects::nonNull)
                .forEach(jumpResultDTO -> {
                    jumpResultToDataRaceService.save(new JumpResultToDataRaceDTO().dataRaceId(dataRaceId).jumpResultId(jumpResultDTO.getId()));
                    sendMessage(jumpResultDTO);
                });
    }

    private void sendMessage(JumpResultDTO jumpResult) {
        MessageDTO messageDTO;
        try {
            messageDTO = new MessageDTO().addProperties(MessagePropertiesConst.JUMP_RESULT.getValue(), objectMapper.writeValueAsString(jumpResult));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        rabbitmqProducer.sendMessage(exchange, importSkiJumperDataListener, messageDTO);
    }
}
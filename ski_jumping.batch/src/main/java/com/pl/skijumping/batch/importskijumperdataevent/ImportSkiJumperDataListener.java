package com.pl.skijumping.batch.importskijumperdataevent;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.*;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.JumpResultToSkiJumperService;
import com.pl.skijumping.service.SkiJumperService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ImportSkiJumperDataListener {

    private final SkiJumperService skiJumperService;
    private final JumpResultToSkiJumperService jumpResultToSkiJumperService;
    private final DiagnosticMonitor diagnosticMonitor;
    private final RabbitmqProducer rabbitmqProducer;
    private final String host;
    private final String sourceImportEventListener;
    private final String exchange;
    private final String importSkiJumperDataListenerEvent;

    public ImportSkiJumperDataListener(SkiJumperService skiJumperService,
                                       JumpResultToSkiJumperService jumpResultToSkiJumperService,
                                       DiagnosticMonitor diagnosticMonitor,
                                       @Value("${skijumping.settings.skiJumperDataHost}") String host,
                                       @Value("${skijumping.rabbitmq.queues.importSkiJumperDataListener}") String importSkiJumperDataListenerEvent,
                                       @Value("${skijumping.rabbitmq.queues.sourceImportEventListener}") String sourceImportEventListener,
                                       @Value("${skijumping.rabbitmq.exchange}") String exchange,
                                       RabbitmqProducer rabbitmqProducer) {
        this.skiJumperService = skiJumperService;
        this.jumpResultToSkiJumperService = jumpResultToSkiJumperService;
        this.diagnosticMonitor = diagnosticMonitor;
        this.host = host;
        this.sourceImportEventListener = sourceImportEventListener;
        this.exchange = exchange;
        this.importSkiJumperDataListenerEvent = importSkiJumperDataListenerEvent;
        this.rabbitmqProducer = rabbitmqProducer;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importSkiJumperDataListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true")
    ))
    public void importJumperData(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return;
        }

        JumpResultDTO jumpResultDTO = (JumpResultDTO) messageDTO.getProperties().get(MessageProperties.JUMP_RESULT.getValue());
        Optional<SkiJumperDTO> skiJumperDTO = skiJumperService.findByFisCode(jumpResultDTO.getFisCodeId());

        if (skiJumperDTO .isPresent()) {
            saveJumpResultToSkiJumper(skiJumperDTO.get(), jumpResultDTO);
            return;
        }

        ParseSkiJumperData parseSkiJumperData = new ParseSkiJumperData(diagnosticMonitor);
        String filePath = messageDTO.getFilePath();

        if (filePath != null && !filePath.isEmpty()) {
            SkiJumperDTO parsedSkiJumperDTO = skiJumperService.save(parseSkiJumperData.importData(jumpResultDTO, filePath));
            saveJumpResultToSkiJumper(parsedSkiJumperDTO, jumpResultDTO);
        } else {
            importFile(messageDTO, jumpResultDTO);
        }
    }

    private void importFile(MessageDTO messageDTO, JumpResultDTO jumpResult) {
        messageDTO.addProperties(MessageProperties.DESTINATION_TARGET.getValue(), importSkiJumperDataListenerEvent)
                .addProperties(MessageProperties.DOWNLOAD_SOURCE_URL.getValue(), String.format(host, jumpResult.getCompetitorId()))
                .addProperties(MessageProperties.FILE_NAME.getValue(), FileScannerConst.prepapeFileName(FileScannerConst.FILE_SKI_JUMPER, "_" + jumpResult.getFisCodeId()));

        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }

    private void saveJumpResultToSkiJumper(SkiJumperDTO skiJumperDTO, JumpResultDTO jumpResultDTO) {
        if(skiJumperDTO == null || jumpResultDTO == null) {
            return;
        }
        jumpResultToSkiJumperService.save(new JumpResultToSkiJumperDTO().skiJumperId(skiJumperDTO.getId()).jumpResultId(jumpResultDTO.getId()));
    }
}

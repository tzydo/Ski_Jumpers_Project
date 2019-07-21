package com.pl.skijumping.batch.importevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImportEventIdListener {

    private final DiagnosticMonitor diagnosticMonitor;
    private final String eventHost;
    private final String sourceImportEventListener;
    private final String importDataRaceEventListener;
    private final String exchange;
    private final RabbitmqProducer rabbitmqProducer;

    public ImportEventIdListener(DiagnosticMonitor diagnosticMonitor,
                                 @Value("${skijumping.settings.eventHost}") String eventHost,
                                 @Value("${skijumping.rabbitmq.queues.sourceImportEventListener}") String sourceImportEventListener,
                                 @Value("${skijumping.rabbitmq.queues.importDataRaceEventListener}") String importDataRaceEventListener,
                                 @Value("${skijumping.rabbitmq.exchange}") String exchange,
                                 RabbitmqProducer rabbitmqProducer) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.eventHost = eventHost;
        this.sourceImportEventListener = sourceImportEventListener;
        this.importDataRaceEventListener = importDataRaceEventListener;
        this.exchange = exchange;
        this.rabbitmqProducer = rabbitmqProducer;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.importEventIdListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true"),
            key = "importEventIdListener"
    ))
    public void importEvent(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return;
        }
        Integer seasonCode = messageDTO.getProperties().getIntValue(MessagePropertiesConst.SEASON_CODE.getValue());
        if(seasonCode == null) {
            diagnosticMonitor.logWarn("Season code cannot be null");
            return;
        }
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readText = dataReader.read(messageDTO.getFilePath());
        if (readText == null || readText.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot read event id from empty file");
            return;
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        matchingWords.getEventIds(readText)
                .forEach(event -> sendMessage(event, seasonCode));
    }

    private void sendMessage(String eventId, Integer seasonCode) {
        String url = String.format(eventHost, eventId, seasonCode);
        MessageDTO messageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), importDataRaceEventListener)
                .addProperties(MessagePropertiesConst.EVENT_ID.getValue(), eventId)
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), seasonCode)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), FileScannerConst.prepareFileName(FileScannerConst.FILE_DATA_IMPORT, seasonCode + "_" + eventId))
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), url);
        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }
}

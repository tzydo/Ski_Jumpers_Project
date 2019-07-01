package com.pl.skijumping.batch.sourceimporterevent;

import com.pl.skijumping.client.HtmlDownloader;
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

import java.nio.file.Path;

@Component
public class SourceImporterEvent {

    private final RabbitmqProducer rabbitmqProducer;
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;
    private final String directoryPath;
    private final String exchange;

    public SourceImporterEvent(RabbitmqProducer rabbitmqProducer,
                               DiagnosticMonitor diagnosticMonitor,
                               HtmlDownloader htmlDownloader,
                               @Value("${skijumping.settings.directory}") String directoryPath,
                               @Value("${skijumping.rabbitmq.exchange}") String exchange) {
        this.rabbitmqProducer = rabbitmqProducer;
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
        this.directoryPath = directoryPath;
        this.exchange = exchange;
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.sourceImportEventListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true")
    ))
    public void sourceImport(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return;
        }

        String url = messageDTO.getProperties().getStringValue(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue());
        String fileName = messageDTO.getProperties().getStringValue(MessagePropertiesConst.FILE_NAME.getValue());
        String destinationTarget = messageDTO.getProperties().getStringValue(MessagePropertiesConst.DESTINATION_TARGET.getValue());
        SourceDownloader sourceDownloader = new SourceDownloader(diagnosticMonitor, htmlDownloader);
        Path filePath = sourceDownloader.download(directoryPath, url, fileName);
        if(filePath == null) {
            diagnosticMonitor.logError(String.format("Error during download file: %s , from url: %s", fileName, url), this.getClass());
            return;
        }

        messageDTO.filePath(filePath.toString());
        rabbitmqProducer.sendMessage(exchange, destinationTarget, messageDTO);
    }
}

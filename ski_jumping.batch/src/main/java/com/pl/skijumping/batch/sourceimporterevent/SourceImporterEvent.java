package com.pl.skijumping.batch.sourceimporterevent;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.ImportedFileDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import com.pl.skijumping.service.ImportedFileService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class SourceImporterEvent {
    private static Map<String, String> fileUrlMap = new HashMap<>();
    private final ImportedFileService importedFileService;
    private final RabbitmqProducer rabbitmqProducer;
    private final DiagnosticMonitor diagnosticMonitor;
    private final HtmlDownloader htmlDownloader;
    private final String directoryPath;
    private final String exchange;

    public SourceImporterEvent(ImportedFileService importedFileService,
                               RabbitmqProducer rabbitmqProducer,
                               DiagnosticMonitor diagnosticMonitor,
                               HtmlDownloader htmlDownloader,
                               @Value("${skijumping.settings.directory}") String directoryPath,
                               @Value("${skijumping.rabbitmq.exchange}") String exchange) {
        this.importedFileService = importedFileService;
        this.rabbitmqProducer = rabbitmqProducer;
        this.diagnosticMonitor = diagnosticMonitor;
        this.htmlDownloader = htmlDownloader;
        this.directoryPath = directoryPath;
        this.exchange = exchange;
    }

    @PostConstruct
    public void fillFileUrlMap() {
        SourceImporterEvent.fileUrlMap = importedFileService.findAllInMap();
    }

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "${skijumping.rabbitmq.queues.sourceImportEventListener}", durable = "true"),
            exchange = @Exchange(value = "${skijumping.rabbitmq.exchange}", type = ExchangeTypes.TOPIC, durable = "true"),
            key = "sourceImportEventListener"
    ))
    public void sourceImport(MessageDTO messageDTO) {
        if(messageDTO == null) {
            return;
        }

        String url = messageDTO.getProperties().getStringValue(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue());
        String fileName = messageDTO.getProperties().getStringValue(MessagePropertiesConst.FILE_NAME.getValue());
        String destinationTarget = messageDTO.getProperties().getStringValue(MessagePropertiesConst.DESTINATION_TARGET.getValue());

        if(fileUrlMap.containsKey(fileName)){
            diagnosticMonitor.logWarn(String.format("File: %s was successfully download earlier!", fileName));
            return;
        }
        fileUrlMap.put(fileName, url);
        importedFileService.save(new ImportedFileDTO().file(fileName).url(url));
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

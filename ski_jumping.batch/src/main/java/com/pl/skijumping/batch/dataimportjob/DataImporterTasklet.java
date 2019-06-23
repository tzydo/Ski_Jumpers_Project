package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.IOException;
import java.time.LocalDate;

public class DataImporterTasklet implements Tasklet {
    private final String host;
    private final DiagnosticMonitor diagnosticMonitor;
    private Integer monthToDownload;
    private final RabbitmqProducer rabbitmqProducer;
    private final String sourceImportEventListener;
    private final String importEventIdListener;
    private final String exchange;

    public DataImporterTasklet(String host,
                               String sourceImportEventListener,
                               String importEventIdListener,
                               String exchange,
                               DiagnosticMonitor diagnosticMonitor,
                               Integer monthToDownload,
                               RabbitmqProducer rabbitmqProducer) {
        this.host = host;
        this.diagnosticMonitor = diagnosticMonitor;
        this.monthToDownload = monthToDownload;
        this.rabbitmqProducer = rabbitmqProducer;
        this.sourceImportEventListener = sourceImportEventListener;
        this.importEventIdListener = importEventIdListener;
        this.exchange = exchange;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws IOException {
        if (monthToDownload == null || monthToDownload > DataImporterConst.MAX_PREVIOUS_MONTH) {
            diagnosticMonitor.logWarn("No set count of previous months to download");
            monthToDownload = DataImporterConst.DEFAULT_MONTH_TO_DOWNLOAD;
        }

        String additionalDate = (String) chunkContext.getStepContext().getJobParameters().get("date");
        LocalDate localDate = additionalDate != null ? LocalDate.parse(additionalDate) : LocalDate.now();
        DataImporterUtil.generateListOfDates(localDate.getYear(), localDate.getMonthValue(), monthToDownload)
                .forEach(this::sendMessage);

        return RepeatStatus.FINISHED;
    }

    private void sendMessage(LocalDate localDate) {
        String url = String.format(host, localDate.getYear(), localDate.getMonthValue(), localDate.getYear());
        MessageDTO messageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), importEventIdListener)
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), localDate.getYear())
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), FileScannerConst.prepareFileName(FileScannerConst.FILE_EVENT_ID, localDate.getMonthValue() + "_" + localDate.getYear()))
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), url);
        rabbitmqProducer.sendMessage(exchange, sourceImportEventListener, messageDTO);
    }
}

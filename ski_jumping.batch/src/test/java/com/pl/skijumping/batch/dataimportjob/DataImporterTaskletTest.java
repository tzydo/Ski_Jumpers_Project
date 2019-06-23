package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DataImporterTaskletTest {

    private static final String LOCALHOST = "http://localhost:8080";
    private static final Integer YEAR_TO_DOWNLOAD = 2;
    private static final String SOURCE_EVENT_LISTENER_ID_TEST = "sourceEventListenerIdTest";
    private static final String IMPORT_EVENT_ID_LISTENER_TEST = "importEventIdListenerTest";
    private static final String EXCHANGE_TEST = "exchangeTest";

    @Mock
    private DiagnosticMonitor diagnosticMonitor;
    @Mock
    private ChunkContext chunkContext;

    private StepExecution stepExecution = new StepExecution("systemCommandStep",
            new JobExecution(new JobInstance(1L, "systemCommandJob")
                    , 1L, new JobParameters(), "configurationName"));

    @Test
    public void testExecute() throws Exception {
        StepContribution stepContribution = stepExecution.createStepContribution();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("date", "2019-02-01");

        StepContext stepContext = Mockito.mock(StepContext.class);
        Mockito.when(stepContext.getJobParameters()).thenReturn(objectMap);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        chunkContext.getStepContext().getJobParameters().get("date");

        RabbitmqProducer mockRabbitmqProducer = Mockito.mock(RabbitmqProducer.class);
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(LOCALHOST,
                SOURCE_EVENT_LISTENER_ID_TEST,
                IMPORT_EVENT_ID_LISTENER_TEST,
                EXCHANGE_TEST,
                diagnosticMonitor,
                YEAR_TO_DOWNLOAD,
                mockRabbitmqProducer
        );
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);

        MessageDTO firstExpectedMessageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), 2019)
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), LOCALHOST)
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), IMPORT_EVENT_ID_LISTENER_TEST)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), "event_2_2019.txt");

        MessageDTO secondExpectedMessageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), 2019)
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), LOCALHOST)
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), IMPORT_EVENT_ID_LISTENER_TEST)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), "event_1_2019.txt");

        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Mockito.verify(mockRabbitmqProducer, Mockito.times(1)).sendMessage(EXCHANGE_TEST, SOURCE_EVENT_LISTENER_ID_TEST, firstExpectedMessageDTO);
        Mockito.verify(mockRabbitmqProducer, Mockito.times(1)).sendMessage(EXCHANGE_TEST, SOURCE_EVENT_LISTENER_ID_TEST, secondExpectedMessageDTO);
    }

    @Test
    public void testExecuteWhenMoreThenMaxMonths() throws Exception {
        StepContribution stepContribution = stepExecution.createStepContribution();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("date", "2019-02-01");

        StepContext stepContext = Mockito.mock(StepContext.class);
        Mockito.when(stepContext.getJobParameters()).thenReturn(objectMap);
        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        chunkContext.getStepContext().getJobParameters().get("date");

        RabbitmqProducer mockRabbitmqProducer = Mockito.mock(RabbitmqProducer.class);
        DataImporterTasklet dataImporterTasklet = new DataImporterTasklet(LOCALHOST,
                SOURCE_EVENT_LISTENER_ID_TEST,
                IMPORT_EVENT_ID_LISTENER_TEST,
                EXCHANGE_TEST,
                diagnosticMonitor,
                500,
                mockRabbitmqProducer
        );
        RepeatStatus execute = dataImporterTasklet.execute(stepContribution, chunkContext);

        MessageDTO firstExpectedMessageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), 2019)
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), LOCALHOST)
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), IMPORT_EVENT_ID_LISTENER_TEST)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), "event_2_2019.txt");

        MessageDTO secondExpectedMessageDTO = new MessageDTO()
                .addProperties(MessagePropertiesConst.SEASON_CODE.getValue(), 2019)
                .addProperties(MessagePropertiesConst.DOWNLOAD_SOURCE_URL.getValue(), LOCALHOST)
                .addProperties(MessagePropertiesConst.DESTINATION_TARGET.getValue(), IMPORT_EVENT_ID_LISTENER_TEST)
                .addProperties(MessagePropertiesConst.FILE_NAME.getValue(), "event_1_2019.txt");

        Assertions.assertThat(execute).isEqualTo(RepeatStatus.FINISHED);
        Mockito.verify(mockRabbitmqProducer, Mockito.times(1)).sendMessage(EXCHANGE_TEST, SOURCE_EVENT_LISTENER_ID_TEST, firstExpectedMessageDTO);
        Mockito.verify(mockRabbitmqProducer, Mockito.times(1)).sendMessage(EXCHANGE_TEST, SOURCE_EVENT_LISTENER_ID_TEST, secondExpectedMessageDTO);
    }
}
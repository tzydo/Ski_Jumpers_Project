package com.pl.skijumping.batch.importevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.rabbitmq.producer.RabbitmqProducer;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ImportEventIdListenerTest {

    private static final String HOST = "host";
    private static final String SOURCE_IMPORT_EVENT_LISTENER = "sourceImportEventListener";
    private static final String IMPORT_DATA_RACE_EVENT_LISTENER = "importDataRaceEventListener";
    private static final String EXCHANGE = "exchange";

    @Test
    public void importEvent() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        RabbitmqProducer rabbitmqProducer = Mockito.mock(RabbitmqProducer.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put(MessagePropertiesConst.SEASON_CODE.getValue(), 2018);
        MessageDTO messageDTO = new MessageDTO().filePath(Paths.get(new ClassPathResource("import/eventIdImport.txt").getURI()).toString())
                .properties(properties);

        ImportEventIdListener importEventIdListener = new ImportEventIdListener(diagnosticMonitorMock, HOST, SOURCE_IMPORT_EVENT_LISTENER, IMPORT_DATA_RACE_EVENT_LISTENER, EXCHANGE, rabbitmqProducer);
        importEventIdListener.importEvent(messageDTO);
        Mockito.verify(rabbitmqProducer, Mockito.times(7)).sendMessage(Mockito.any(), Mockito.any(), Mockito.any());
    }
}
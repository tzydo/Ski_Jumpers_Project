package com.pl.skijumping.batch.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Profile;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class DataReaderTest {

    @Test
    public void readTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read("testSkiJumper.txt");
        Assertions.assertThat(readWords).isNotNull();
        Assertions.assertThat(readWords).isNotEmpty();
    }

    @Test
    public void readWhenNullTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read(null);
        Assertions.assertThat(readWords).isNull();
    }

    @Test
    public void readWhenEmptyTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readWords = dataReader.read("emptyTest");
        Assertions.assertThat(readWords).isNull();
    }
}
package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class DataReaderTest {

    @Test
    public void readTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader("testSkiJumper.txt", diagnosticMonitor);
        String readWords = dataReader.read();
        Assertions.assertThat(readWords).isNotNull();
        Assertions.assertThat(readWords).isNotEmpty();
    }

    @Test
    public void readWhenNullTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader(null, diagnosticMonitor);
        String readWords = dataReader.read();
        Assertions.assertThat(readWords).isNull();
    }

    @Test
    public void readWhenEmptyTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReader dataReader = new DataReader("emptyTest", diagnosticMonitor);
        String readWords = dataReader.read();
        Assertions.assertThat(readWords).isNull();
    }
}
package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DataReaderBatchTest {
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    public void readWhenNullTest() {
        DataReaderBatch findRaceDataReaderBatch = new DataReaderBatch(null, diagnosticMonitor);
        String readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }

    @Test
    public void openWhenFileEmptyTest() {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReaderBatch findRaceDataReaderBatch = new DataReaderBatch("emptyTest.txt", diagnosticMonitor);
        findRaceDataReaderBatch.open(executionContext);
        String readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }

    @Test
    public void openWhenIncorrectRegexpTest() throws IOException {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReaderBatch findRaceDataReaderBatch =
                new DataReaderBatch("testSkiJumper.txt", diagnosticMonitor);
        findRaceDataReaderBatch.open(executionContext);
        String readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }

    @Test
    public void openWhenNotFoundMatchingWordsTest() throws IOException {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReaderBatch findRaceDataReaderBatch =
                new DataReaderBatch("notMatchingWords.txt", diagnosticMonitor);
        findRaceDataReaderBatch.open(executionContext);
        String readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }
}
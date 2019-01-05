package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.findracedatajob.reader.DataReaderBatch;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ExecutionContext;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DataReaderBatchTest {
    @Mock
    private ExecutionContext executionContext;
    @Mock
    private DiagnosticMonitor diagnosticMonitor;

    @Test
    public void readWhenNullTest() {
        DataReaderBatch findRaceDataReaderBatch = new DataReaderBatch(null, diagnosticMonitor);
        Path readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }

    @Test
    public void openWhenEmptyDirectoryTest() {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReaderBatch findRaceDataReaderBatch = new DataReaderBatch("emptyDirectory", diagnosticMonitor);
        findRaceDataReaderBatch.open(executionContext);
        Path readValue = findRaceDataReaderBatch.read();
        Assertions.assertThat(readValue).isNull();
    }

    @Test
    public void getCorrectlyFilesPathFromDirectoryFilesFenTest() throws IOException {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        DataReaderBatch findRaceDataReaderBatch = new DataReaderBatch("eventDirectory", diagnosticMonitor);
        findRaceDataReaderBatch.open(executionContext);

        List<Path> expectedPathList = Arrays.asList(
                FileUtil.getPath(FileUtil.getResource(), "eventDirectory", "event_2018.txt"),
                FileUtil.getPath(FileUtil.getResource(), "eventDirectory", "event_2019.txt")
        );

        Assertions.assertThat(expectedPathList).contains(findRaceDataReaderBatch.read());
        Assertions.assertThat(expectedPathList).contains(findRaceDataReaderBatch.read());
        Assertions.assertThat(findRaceDataReaderBatch.read()).isNull();
    }
}
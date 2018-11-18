package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
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

@RunWith(MockitoJUnitRunner.class)
public class FindTournamentYearReaderBatchTest {

    @Mock
    private ExecutionContext executionContext;

    @Test
    public void openWhenNullDirectoryTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearReaderBatch findTournamentYearReaderBatch = new FindTournamentYearReaderBatch(null, diagnosticMonitorMock);
        findTournamentYearReaderBatch.open(executionContext);
        Throwable throwable = Assertions.catchThrowable(findTournamentYearReaderBatch::read);
        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }

    @Test
    public void openTest() throws InternalServiceException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        Path fileToPath = FileUtil.getPath("testSkiJumper.txt");
        FindTournamentYearReaderBatch findTournamentYearReaderBatch = new FindTournamentYearReaderBatch(
                fileToPath.toString(), diagnosticMonitorMock);
        findTournamentYearReaderBatch.open(executionContext);
        Assertions.assertThat(findTournamentYearReaderBatch.read()).isNotNull();
    }
}
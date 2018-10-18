package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ExecutionContext;

@RunWith(MockitoJUnitRunner.class)
public class FindTournamentYearReaderBatchTest {

    @Mock
    private ExecutionContext executionContext;

    @Test
    public void read() {
    }

    @Test
    public void openWhenNullDirectoryTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearReaderBatch findTournamentYearReaderBatch = new FindTournamentYearReaderBatch(null, diagnosticMonitorMock);
        findTournamentYearReaderBatch.open(executionContext);
        Assertions.assertThat(findTournamentYearReaderBatch.read()).isNull();
    }

    @Test
    public void openTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearReaderBatch findTournamentYearReaderBatch = new FindTournamentYearReaderBatch(
                "testSkiJumper.txt", diagnosticMonitorMock);
        findTournamentYearReaderBatch.open(executionContext);
        Assertions.assertThat(findTournamentYearReaderBatch.read()).isNotNull();
    }
}
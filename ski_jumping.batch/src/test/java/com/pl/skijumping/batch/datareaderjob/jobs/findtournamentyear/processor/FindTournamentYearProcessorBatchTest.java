package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FindTournamentYearProcessorBatchTest {

    @Test
    public void processWhenNullTest() throws Exception {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessorBatch.process(null);

        Assertions.assertThat(processedWords).isEqualTo(new ArrayList<>());
    }

    @Test
    public void processWhenNotFoundMatchingWordsTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        Throwable throwable = Assertions.catchThrowable(() -> {
            findTournamentYearProcessorBatch.process("test test test");
        });

        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }

    @Test
    public void processTest() throws Exception {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessorBatch.process(
                "<option value=\"2018\" >2018</option>");
        Assertions.assertThat(processedWords).isNotEmpty();
        Assertions.assertThat(processedWords).hasSize(1);
        Assertions.assertThat(processedWords.get(0)).isEqualTo("2018");
    }
}
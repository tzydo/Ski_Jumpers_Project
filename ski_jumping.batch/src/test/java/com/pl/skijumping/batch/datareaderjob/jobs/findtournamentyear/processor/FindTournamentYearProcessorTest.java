package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FindTournamentYearProcessorTest {

    @Test
    public void processWhenNullTest() throws Exception {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessor findTournamentYearProcessor = new FindTournamentYearProcessor(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessor.process(null);

        Assertions.assertThat(processedWords).isEqualTo(new ArrayList<>());
    }

    @Test
    public void processWhenNotFoundMatchingWordsTest() throws Exception {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessor findTournamentYearProcessor = new FindTournamentYearProcessor(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessor.process("test test test");

        Assertions.assertThat(processedWords).isEqualTo(new ArrayList<>());
    }

    @Test
    public void processTest() throws Exception {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessor findTournamentYearProcessor = new FindTournamentYearProcessor(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessor.process(
                "<option value=\"2018\" >2018</option>");
        Assertions.assertThat(processedWords).isNotEmpty();
        Assertions.assertThat(processedWords).hasSize(1);
        Assertions.assertThat(processedWords.get(0)).isEqualTo("2018");
    }
}
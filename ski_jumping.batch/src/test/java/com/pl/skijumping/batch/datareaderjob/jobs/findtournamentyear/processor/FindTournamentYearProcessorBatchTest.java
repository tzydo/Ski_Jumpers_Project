package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindTournamentYearProcessorBatchTest {

    @Test
    public void processWhenNullTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessorBatch.process(null);

        Assertions.assertThat(processedWords).isEqualTo(new ArrayList<>());
    }

    @Test
    public void processWhenNotFoundMatchingWordsTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        List<String> tournamentYearList = findTournamentYearProcessorBatch.process("test test test");
        Assertions.assertThat(tournamentYearList).isEmpty();
    }

    @Test
    public void processTest() {
        String words = "<select id=\"seasoncode\" tabindex=\"3\" data-module=\"select\" title=\"select a season\" onchange=\"fct_change_calendar_season(this);\" name=\"seasoncode\">\n" +
                "\t<option value=\"2019\" selected=\"selected\">2019</option>\n" +
                "\t<option value=\"2018\">2018</option>\n" +
                "\t<option value=\"2017\">2017</option>\n" +
                "\t<option value=\"2016\">2016</option>";

        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        FindTournamentYearProcessorBatch findTournamentYearProcessorBatch = new FindTournamentYearProcessorBatch(diagnosticMonitorMock);
        List<String> processedWords = findTournamentYearProcessorBatch.process(words);
        Assertions.assertThat(processedWords).isNotEmpty();
        Assertions.assertThat(processedWords).hasSize(4);
        Assertions.assertThat(processedWords).containsExactlyElementsOf(Arrays.asList("2019", "2018", "2017", "2016"));
    }
}
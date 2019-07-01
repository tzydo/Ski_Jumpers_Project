package com.pl.skijumping.batch.importdataraceevent.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class FindRaceDataContentTest {

    @Test
    public void findTemplateDataWhenNullPathTest() {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        FindRaceDataContent findRaceDataContent = new FindRaceDataContent(diagnosticMonitor);
        Set<String> templateData = findRaceDataContent.findTemplateData(null);
        Assertions.assertThat(templateData).isEmpty();
    }

    @Test
    public void findTemplateDataTest() throws IOException {
        DiagnosticMonitor diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
        FindRaceDataContent findRaceDataContent = new FindRaceDataContent(diagnosticMonitor);
        Set<String> templateData = findRaceDataContent.findTemplateData(FileUtil.getPath(FileUtil.getResource(), "eventDirectory", "event_2018.txt"));
        Assertions.assertThat(templateData).isNotEmpty();
        Assertions.assertThat(templateData).containsAll(Arrays.asList("test", "test3", "test2"));
    }
}
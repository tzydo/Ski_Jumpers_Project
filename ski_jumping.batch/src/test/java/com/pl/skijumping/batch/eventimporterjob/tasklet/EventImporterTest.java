package com.pl.skijumping.batch.eventimporterjob.tasklet;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventImporterTest {

    @Test
    public void importEventsTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        EventImporter eventImporter = new EventImporter(diagnosticMonitorMock);

        Path filePath = FileUtil.getPath(FileUtil.getResource(), "import_eventIdImport.txt");
        Path copiedPath = FileUtil.getPath(FileUtil.getResource(), "copyEventIdImport.txt");
        Path copyFile = Files.copy(filePath, copiedPath);

        List<Pair<String, String>> importEvents = eventImporter.importEvents(copyFile);
        Assertions.assertThat(importEvents).isNotEmpty();
        Assertions.assertThat(importEvents).hasSize(4);
        Assertions.assertThat(importEvents).containsAll(Arrays.asList(new Pair<>("copyEventIdImport", "39207"), new Pair<>("copyEventIdImport", "39224"), new Pair<>("copyEventIdImport", "39038"), new Pair<>("copyEventIdImport", "39225")));
        copyFile.toFile().delete();
    }

    @Test
    public void importEventsWhenNullTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        EventImporter eventImporter = new EventImporter(diagnosticMonitorMock);
        List<Pair<String, String>> importEvents = eventImporter.importEvents(null);

        Assertions.assertThat(importEvents).isEmpty();
    }

    @Test
    public void importEventsWhenEmptyTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        EventImporter eventImporter = new EventImporter(diagnosticMonitorMock);
        List<Pair<String, String>> importEvents = eventImporter.importEvents(FileUtil.getPath(FileUtil.getResource(), "emptyTest.txt"));

        Assertions.assertThat(importEvents).isEmpty();
    }
}
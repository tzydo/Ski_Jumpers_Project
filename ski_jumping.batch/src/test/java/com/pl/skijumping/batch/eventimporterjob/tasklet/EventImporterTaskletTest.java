package com.pl.skijumping.batch.eventimporterjob.tasklet;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.ExitStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class EventImporterTaskletTest {

    @Test
    public void importEventTest() throws IOException, InternalServiceException {
        Path directoryPath = FileUtil.createDirectory(FileUtil.getResourcePath(), "event");
        Path eventFile = FileUtil.getPath(FileUtil.getResource(), "import_eventIdImport.txt");
        Files.copy(eventFile, FileUtil.getPath(directoryPath.toString(), "import_eventIdImport-2018.txt"));


        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        HtmlDownloader mockHtmlDownloader = Mockito.mock(HtmlDownloader.class);
        Mockito.when(mockHtmlDownloader.downloadSource(Mockito.any(), Mockito.anyString())).thenReturn(eventFile);
        EventImporterTasklet eventImporterTasklet = new EventImporterTasklet(diagnosticMonitorMock, directoryPath.toFile().getName(), mockHtmlDownloader, "testUrl");
        ExitStatus exitStatus = eventImporterTasklet.importEvent();
        Assertions.assertThat(exitStatus.getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());

        List<File> fileList = FileUtil.getFiles(directoryPath);
        Assertions.assertThat(fileList).hasSize(4);
        Assertions.assertThat(fileList.stream().map(File::getName).collect(Collectors.toList())).containsAll(Arrays.asList("event_2018_39224.txt", "event_2018_39038.txt", "event_2018_39207.txt", "event_2018_39225.txt"));

        fileList.forEach(File::delete);
        directoryPath.toFile().delete();
    }

    @Test
    public void importEventWhenWrongFile() throws IOException, InternalServiceException {
        Path directoryPath = FileUtil.createDirectory(FileUtil.getResourcePath(), "event");
        Path eventFile = FileUtil.createFile(directoryPath, "import_eventIdImport.txt");


        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        HtmlDownloader mockHtmlDownloader = Mockito.mock(HtmlDownloader.class);
        Mockito.when(mockHtmlDownloader.downloadSource(Mockito.any(), Mockito.anyString())).thenReturn(eventFile);

        EventImporterTasklet eventImporterTasklet = new EventImporterTasklet(diagnosticMonitorMock, directoryPath.getFileName().toString(), mockHtmlDownloader, "testUrl");
        ExitStatus exitStatus = eventImporterTasklet.importEvent();
        Assertions.assertThat(exitStatus.getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());

        List<File> fileList = FileUtil.getFiles(directoryPath);
        Assertions.assertThat(fileList).hasSize(1);
        Assertions.assertThat(fileList.stream().map(File::getName).collect(Collectors.toList())).containsAll(Collections.singletonList("import_eventIdImport.txt"));

        FileUtil.deleteFile(FileUtil.getPath(directoryPath.toString(), "import_eventIdImport.txt"));
        eventFile.toFile().delete();
        directoryPath.toFile().delete();
    }

    @Test
    public void importEventWhenHtmlDownloaderReturnNull() throws IOException, InternalServiceException {
        Path directoryPath = FileUtil.createDirectory(FileUtil.getResourcePath(), "event");
        Path eventFile = FileUtil.getPath(FileUtil.getResource(), "import_eventIdImport.txt");
        Files.copy(eventFile, FileUtil.getPath(directoryPath.toString(), "import_eventIdImport-2018.txt"));


        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        HtmlDownloader mockHtmlDownloader = Mockito.mock(HtmlDownloader.class);
        Mockito.when(mockHtmlDownloader.downloadSource(Mockito.any(), Mockito.anyString())).thenReturn(null);

        EventImporterTasklet eventImporterTasklet = new EventImporterTasklet(diagnosticMonitorMock, directoryPath.getFileName().toString(), mockHtmlDownloader, "testUrl");
        Throwable throwable = Assertions.catchThrowable(() -> eventImporterTasklet.importEvent());

        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);

        directoryPath.toFile().delete();
    }
}
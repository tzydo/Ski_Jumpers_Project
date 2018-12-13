package com.pl.skijumping.batch.eventimporterjob.tasklet;

import com.pl.skijumping.batch.util.FileScanner;
import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.client.HtmlDownloader;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.ExitStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class EventImporterTasklet {

    private static final String TXT = ".txt";
    private final DiagnosticMonitor diagnosticMonitor;
    private final String directoryName;
    private final HtmlDownloader htmlDownloader;
    private final String host;

    EventImporterTasklet(DiagnosticMonitor diagnosticMonitor,
                         String directoryName,
                         HtmlDownloader htmlDownloader,
                         String host) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.directoryName = directoryName;
        this.htmlDownloader = htmlDownloader;
        this.host = host;
    }

    ExitStatus importEvent() throws InternalServiceException, IOException {
        String message;
        if (directoryName == null || directoryName.isEmpty()) {
            message = "Cannot get files from null directory path";
            diagnosticMonitor.logWarn(message);
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), message);
        }

        EventImporter eventImporter = new EventImporter(diagnosticMonitor);
        Set<Path> fileList = FileScanner.getPathList(FileScannerConst.FILE_DATA_IMPORT, FileUtil.getPath(FileUtil.getResource(), directoryName));
        Set<Pair<String, String>> eventIdSets = fileList.stream()
                .filter(Objects::nonNull)
                .map(eventImporter::importEvents)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        if (eventIdSets.isEmpty()) {
            return ExitStatus.COMPLETED;
        }

        removeFiles(fileList);

        for (Pair<String, String> eventId : eventIdSets) {
            Path path = FileUtil.createFile(FileUtil.getPath(FileUtil.getResource(), this.directoryName), this.generateFileName(eventId));
            Path downloadSource = this.htmlDownloader.downloadSource(path, this.generateUrl(eventId.getRight()));
            if (downloadSource == null) {
                FileUtil.deleteFile(path);
                throw new InternalServiceException(String.format("Error during download source for event%s. Cannot download source.", eventId));
            }
        }

        return ExitStatus.COMPLETED;
    }

    private void removeFiles(Set<Path> pathList) throws InternalServiceException {
        for (Path path : pathList) {
            FileUtil.deleteFile(path);
        }
    }

    private String generateUrl(String eventId) {
        return this.host + eventId;
    }

    private String generateFileName(Pair<String, String> event) {
        String[] split = event.getLeft().split("-");
        return FileScannerConst.FILE_EVENT_ID + split[1] + "_" + event.getRight() + TXT;
    }
}

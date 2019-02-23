package com.pl.skijumping.batch.findracedatajob.reader;

import com.pl.skijumping.batch.util.FileScanner;
import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.common.util.FileUtil;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.support.ListItemReader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataReaderBatch implements ItemStreamReader<Path> {
    private final String directoryName;
    private final DiagnosticMonitor diagnosticMonitor;
    private ListItemReader<Path> filePathsItemReader;

    public DataReaderBatch(String directoryName, DiagnosticMonitor diagnosticMonitor) {
        this.directoryName = directoryName;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public Path read() {
        if (filePathsItemReader != null) {
            return filePathsItemReader.read();
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Set<Path> filePath = new HashSet<>();
        try {
            filePath = getPathsFromDirectory(directoryName);
        } catch (IOException e) {
            diagnosticMonitor.logWarn("Error reading file list from directory " + directoryName);
        }
        filePathsItemReader = new ListItemReader<>(new ArrayList<>(filePath));
    }

    private Set<Path> getPathsFromDirectory(String directoryName) throws IOException {
        if (directoryName == null) {
            diagnosticMonitor.logWarn("Directory name cannot be null!");
            return new HashSet<>();
        }
        return FileScanner.getPathList(FileScannerConst.FILE_EVENT_ID, FileUtil.getPath(FileUtil.getResource(), directoryName));
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // not necessary
    }

    @Override
    public void close() throws ItemStreamException {
        // not necessary
    }
}

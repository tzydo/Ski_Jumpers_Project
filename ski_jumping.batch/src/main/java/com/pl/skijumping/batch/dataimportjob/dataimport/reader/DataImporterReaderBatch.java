package com.pl.skijumping.batch.dataimportjob.dataimport.reader;

import com.pl.skijumping.common.util.FileUtil;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.util.Optional;

public class DataImporterReaderBatch implements ItemReader<Boolean> {

    private final String directory;
    private final String fileName;

    public DataImporterReaderBatch(String directory,
                                   String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    @Override
    public Boolean read() throws Exception {
        if (!FileUtil.createDirectory(this.directory)) {
            return false;
        }

        Optional<File> file = FileUtil.getFile(this.fileName);
        if (file.isPresent()) {
            FileUtil.deleteFile(file.get().getPath());
        }

        return FileUtil.createFile(this.fileName);
    }
}

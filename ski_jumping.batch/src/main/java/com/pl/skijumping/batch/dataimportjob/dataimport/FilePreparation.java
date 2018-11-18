package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

class FilePreparation {

    private final String directory;
    private final String fileName;

    FilePreparation(String directory,
                    String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    Path prepare() throws InternalServiceException {
        Path directory = FileUtil.createDirectory(this.directory);
        if (directory == null) {
            return null;
        }

        Path pathToFile = FileUtil.getPath(directory.toString(), this.fileName);
        Optional<File> file = FileUtil.getFile(pathToFile);
        if (file.isPresent()) {
            FileUtil.deleteFile(file.get().toPath());
        }

        return FileUtil.createFile(directory, fileName);
    }
}

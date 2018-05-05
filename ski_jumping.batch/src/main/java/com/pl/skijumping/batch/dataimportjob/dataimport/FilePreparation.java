package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;

import java.io.File;
import java.util.Optional;

class FilePreparation {

    private final String directory;
    private final String fileName;

    FilePreparation(String directory,
                           String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    Boolean prepare() throws InternalServiceException {
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

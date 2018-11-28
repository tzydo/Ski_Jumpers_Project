package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.common.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

class FilePreparation {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePreparation.class);

    public static Path prepareDirectory(String directoryName) {
        Path directoryPath = FileUtil.createDirectory(directoryName);
        if (directoryPath == null) {
            LOGGER.error("Cannot create directory from null!");
            return null;
        }

        return directoryPath;
    }

    public static Path prepareFile(String fileName, Path directoryPath) {
        if (directoryPath == null) {
            LOGGER.error("Cannot create file in null directory");
            return null;
        }
        if (fileName == null || fileName.isEmpty()) {
            LOGGER.error("Cannot create file from null");
            return null;
        }

        Path pathToFile = FileUtil.getPath(directoryPath.toString(), fileName);
        if (pathToFile != null && pathToFile.toFile().exists()) {
            LOGGER.warn("File in path: {} and name: {}  exist", directoryPath, fileName);
            return pathToFile;
        }

        return FileUtil.createFile(directoryPath, fileName);
    }
}

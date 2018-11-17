package com.pl.skijumping.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCreator.class);

    private FileCreator() {
//
    }

    static Path create(Path fileDirectory, String fileName) {
        LOGGER.info("Start creating file: {} into {} directory", fileName, fileDirectory);

        if (fileDirectory == null || fileName == null) {
            LOGGER.error("Cannot create file from null!");
            return null;
        }

        Path pathToFile = Paths.get(fileDirectory.toString(), fileName);
        if (pathToFile.toFile().exists()) {
            LOGGER.info("Cannot create file, file exist");
            return pathToFile;
        }
        try {
            return Files.createFile(Paths.get(fileDirectory.toString(), fileName));
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot create %s file ", fileName));
            return null;
        }
    }
}

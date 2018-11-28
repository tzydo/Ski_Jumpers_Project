package com.pl.skijumping.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DirectoryCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryCreator.class);

    private DirectoryCreator() {
//
    }

    static Path create(Path directoryPath, String directoryName) {
        LOGGER.info("Start creating directory: {} into {}", directoryName, directoryPath);

        if (directoryPath == null || directoryName == null) {
            LOGGER.error("Cannot create directory from null!");
            return null;
        }

        Path pathToDirectory = Paths.get(directoryPath.toString(), directoryName);
        if (pathToDirectory.toFile().isDirectory()) {
            LOGGER.info("Cannot create directory, directory exist");
            return pathToDirectory;
        }

        try {
            return Files.createDirectories(Paths.get(directoryPath.toString(), directoryName));
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot create %s directory ", directoryName));
            return null;
        }
    }
}

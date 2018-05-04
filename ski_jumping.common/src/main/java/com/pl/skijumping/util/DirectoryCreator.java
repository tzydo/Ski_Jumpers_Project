package com.pl.skijumping.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

class DirectoryCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryCreator.class);
    private final String directoryName;

    DirectoryCreator(String directoryName) {
        this.directoryName = directoryName;
    }

    boolean create() {
        LOGGER.info("Start creating directory: {}", directoryName);
        String resourcePath;
        try {
            resourcePath = new ClassPathResource(File.separator).getURL().getPath();
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot create%s directory ", directoryName));
            return false;
        }
        String directoryPath = resourcePath + File.separator + directoryName;
        File directory = new File(directoryPath);
        if (directory.exists()) {
            LOGGER.info("Cannot create tmp directory, file exist");
            return true;
        }
        if (directory.mkdir()) {
            LOGGER.info("Successfully created tmp directory");
            return true;
        }

        LOGGER.info("Cannot create tmp directory, file exist");
        return false;
    }
}

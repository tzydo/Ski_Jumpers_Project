package com.pl.skijumping.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

class FileCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCreator.class);
    private final String fileName;

    FileCreator(String fileName) {
        this.fileName = fileName;
    }

    public Boolean create() {
        LOGGER.info("Start creating file: {}", fileName);
        String resourcePath;
        try {
            resourcePath = new ClassPathResource(File.separator).getURL().getPath();
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot create %s file ", fileName));
            return false;
        }
        File file = new File(resourcePath + File.separator + fileName);
        return createFile(file);
    }

    private boolean createFile(File file) {
        boolean isCreate;
        try {
            isCreate = file.createNewFile();
        } catch (IOException e) {
            LOGGER.error(String.format("Cannot create file: %s", fileName));
            return false;
        }
        if (isCreate) {
            LOGGER.info("Successfully created {} file", fileName);
            return true;
        }

        LOGGER.error("Cannot create file, file exist");
        return false;
    }
}

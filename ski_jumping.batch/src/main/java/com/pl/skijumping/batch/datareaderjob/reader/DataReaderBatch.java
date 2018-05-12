package com.pl.skijumping.batch.datareaderjob.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataReaderBatch implements ItemReader<String>{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataReaderBatch.class);
    private final String filePath;

    public DataReaderBatch(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String read() {
        LOGGER.info("Start reading from file {}", filePath);
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(Paths.get(filePath));
            LOGGER.info("Found {} lines to convert", fileLines.size());
        } catch (IOException e) {
            LOGGER.error("Cannot read content from file {}", filePath);
            return null;
        }

        if (fileLines == null || fileLines.isEmpty()) {
            LOGGER.info("File content is null or empty");
            return null;
        }

        String fileContent = String.join("", fileLines);

        LOGGER.info(String.format("Finish reading file from: %s", filePath));
        return fileContent.replace("  ","");
    }
}

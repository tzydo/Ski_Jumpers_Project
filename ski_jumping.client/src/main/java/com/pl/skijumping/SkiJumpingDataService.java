package com.pl.skijumping;

import com.pl.skijumping.exception.InternalServiceException;
import com.pl.skijumping.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class SkiJumpingDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkiJumpingDataService.class);
    private final String host;
    private final String directory;
    private final String fileName;

    public SkiJumpingDataService(@Value("${skijumping.settings.host}") String host,
                                 @Value("${skijumping.settings.directory}") String directory,
                                 @Value("${skijumping.settings.fileName}") String fileName) {
        this.host = host;
        this.directory = directory;
        this.fileName = fileName;
    }

    public String synchronizeData() throws IOException, InternalServiceException {
        if (!FileUtil.createDirectory(this.directory)) {
            return null;
        }

        Optional<File> file = FileUtil.getFile(this.fileName);
        if (file.isPresent()) {
            FileUtil.deleteFile(file.get().getPath());
        }

        if(!FileUtil.createFile(this.fileName)) {
            return null;
        }

        return downloadSource();
    }

    private String downloadSource() throws IOException {
        HtmlDownloader fileDownloader = new HtmlDownloader(this.fileName, this.host);
        LOGGER.info("Start downloading html source");
        return fileDownloader.downloadSource();
    }
}

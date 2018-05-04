package com.pl.skijumping;

import com.pl.skijumping.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Optional;

class HtmlDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlDownloader.class);
    private final String filePath;
    private final String host;

    HtmlDownloader(String filePath, String host) {
        this.filePath = filePath;
        this.host = host;
    }

    public String downloadSource() throws IOException {
        Optional<File> file = FileUtil.getFile(filePath);
        if (!file.isPresent()) {
            LOGGER.error("File doesn't exist");
            return null;
        }

        InputStream inputStream = getConnection(this.host);
        if (inputStream == null) {
            return null;
        }

        OutputStream outputStream = new FileOutputStream(file.get());
        IOUtils.copy(inputStream, outputStream);
        LOGGER.debug("Finish copying file");
        outputStream.close();
        if (Files.readAllBytes(file.get().toPath()).length == 0) {
            LOGGER.error("Copying file failed");
            return null;
        }

        LOGGER.info("Copying file successfully");
        return file.get().getPath();
    }

    private InputStream getConnection(String host) throws IOException {
        URL connection = new URL(host);
        LOGGER.debug("Open connection with host: {}", host);
        URLConnection urlConnection = connection.openConnection();
        LOGGER.debug("Start downloading source");
        InputStream inputStream = null;
        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            LOGGER.error("Cannot download source from host");
        }
        LOGGER.info("Download source successfully");
        return inputStream;
    }
}

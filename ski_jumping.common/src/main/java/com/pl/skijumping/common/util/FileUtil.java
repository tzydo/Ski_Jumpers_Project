package com.pl.skijumping.common.util;

import com.pl.skijumping.common.exception.InternalServiceException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileUtil {

    private FileUtil() {
    }

    public static void deleteFile(String filePath) throws InternalServiceException {
        File file = new File(filePath);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new InternalServiceException(String.format("Cannot delete file from %s", filePath));
        }
    }

    public static Optional<File> getFile(String filePath) {
        if (filePath == null) {
            return Optional.empty();
        }
        File file;
        try {
            file = new ClassPathResource(filePath).getFile();
        } catch (IOException e) {
            return Optional.empty();
        }
        return Optional.of(file);
    }

    public static Optional<List<File>> getFilesFromPath(String directoryPath) {
        if (directoryPath == null) {
            return Optional.empty();
        }
        File[] file;
        try {
            file = new ClassPathResource(directoryPath).getFile().listFiles();
        } catch (IOException e) {
            return Optional.empty();
        }
        return Optional.of(Arrays.asList(file));
    }

    static String getResourcePath() throws IOException {
        return new ClassPathResource(File.separator).getURL().getPath();
    }

    public static boolean createDirectory(String directoryName) {
        if (directoryName == null) {
            return false;
        }
        DirectoryCreator directoryCreator = new DirectoryCreator(directoryName);
        return directoryCreator.create();
    }

    public static boolean createFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        FileCreator fileCreator = new FileCreator(fileName);
        return fileCreator.create();
    }
}

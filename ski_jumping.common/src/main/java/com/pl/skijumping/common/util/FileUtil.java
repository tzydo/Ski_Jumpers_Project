package com.pl.skijumping.common.util;

import com.pl.skijumping.common.exception.InternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
//
    }

    public static void deleteFile(Path filePath) throws InternalServiceException {
        if (filePath == null) {
            LOGGER.error("Cannot delete null file!");
            return;
        }

        try {
            Files.deleteIfExists(filePath);
            LOGGER.info("Successfully delete file {}", filePath.getFileName());
        } catch (IOException e) {
            LOGGER.info("Cannot delete file {}", filePath.getFileName());
            throw new InternalServiceException(String.format("Cannot delete file from %s", filePath));
        }
    }

    public static Path getPath(String filePath) {
        if (filePath == null) {
            LOGGER.error("Cannot ger file from null!");
            return null;
        }
        return Paths.get(filePath);
    }

    public static Path getPath(String ...values) {
        if(values.length == 0) {
            return null;
        }

        List<String> valueList = Arrays.asList(values);
        String value = String.join(File.separator, valueList);
        return Paths.get(value);
    }


    public static Optional<File> getFile(Path filePath) {
        if (filePath == null) {
            LOGGER.error("Cannot ger file from null path!");
            return Optional.empty();
        }
        if (!filePath.toFile().exists()) {
            LOGGER.error("File doesn't exist!");
            return Optional.empty();
        }
        return Optional.of(filePath.toFile());

    }

    public static Optional<File> getFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            LOGGER.error("Cannot ger file from null path!");
            return Optional.empty();
        }
        Path pathToFile = Paths.get(filePath);
        if (!pathToFile.toFile().exists()) {
            LOGGER.error("File doesn't exist!");
            return Optional.empty();
        }
        return Optional.of(pathToFile.toFile());
    }

    public static List<File> getFiles(String directoryPath) {
        if (directoryPath == null || directoryPath.isEmpty()) {
            LOGGER.error("Cannot get file from null directory");
            return new ArrayList<>();
        }

        Path pathToDirectory = Paths.get(directoryPath);
        if (!pathToDirectory.toFile().isDirectory()) {
            LOGGER.error("Cannot get file from not existing directory");
            return new ArrayList<>();
        }

        try {
            return Files.walk(pathToDirectory).filter(v -> v.toFile().isFile()).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error occurred getting file from directory {}", directoryPath);
            return new ArrayList<>();
        }
    }

    public static List<File> getFiles(Path directoryPath) {
        if (directoryPath == null) {
            LOGGER.error("Cannot get file from null directory");
            return new ArrayList<>();
        }

        if (!directoryPath.toFile().isDirectory()) {
            LOGGER.error("Cannot get file from not existing directory");
            return new ArrayList<>();
        }

        try {
            return Files.walk(directoryPath).filter(v -> v.toFile().isFile()).map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error occurred getting file from directory {}", directoryPath);
            return new ArrayList<>();
        }
    }

    public static List<Path> getPaths(String directoryPath) {
        if (directoryPath == null) {
            LOGGER.error("Cannot get file from null directory");
            return new ArrayList<>();
        }

        Path path = FileUtil.getPath(directoryPath);
        if (!path.toFile().isDirectory()) {
            LOGGER.error("Cannot get file from not existing directory");
            return new ArrayList<>();
        }

        try {
            return Files.walk(path).filter(v -> v.toFile().isFile()).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error occurred getting file from directory {}", directoryPath);
            return new ArrayList<>();
        }
    }

    public static List<Path> getPathList(Path directoryPath) {
        if (directoryPath == null) {
            LOGGER.error("Cannot get file from null directory");
            return new ArrayList<>();
        }

        if (!directoryPath.toFile().isDirectory()) {
            LOGGER.error("Cannot get file from not existing directory");
            return new ArrayList<>();
        }

        try {
            return Files.walk(directoryPath).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error occurred getting file from directory {}", directoryPath);
            return new ArrayList<>();
        }
    }

    public static String getResource() throws IOException {
        return Paths.get(new ClassPathResource(File.separator).getURI()).toString();
    }

    public static Path getResourcePath() {
        try {
            return Paths.get(new ClassPathResource(File.separator).getURI());
        } catch (IOException e) {
            LOGGER.error("Cannot get path to resources");
            return null;
        }
    }

    /***
     * Create directory in resources
     */
    public static Path createDirectory(String directoryName) {
        if (directoryName == null) {
            return null;
        }
        return DirectoryCreator.create(getResourcePath(), directoryName);
    }

    public static Path createDirectory(Path directoryPath, String directoryName) {
        if (directoryName == null || !directoryPath.toFile().isDirectory()) {
            return null;
        }
        return DirectoryCreator.create(directoryPath, directoryName);
    }

    /***
     * Create file in resources
     */
    public static Path createFile(String fileName) {
        if (fileName == null) {
            return null;
        }
        return FileCreator.create(getResourcePath(), fileName);
    }

    public static Path createFile(Path directoryPath, String fileName) {
        if (fileName == null || !directoryPath.toFile().isDirectory()) {
            return null;
        }
        return FileCreator.create(directoryPath, fileName);
    }

    public static String getFileNameWithoutExtensions(Path file) {
        if(file == null || file.getFileName() == null) {
            return "random_empty";
        }

        String[] split = file.getFileName().toString().split("\\.");
        return split[0];
    }
}

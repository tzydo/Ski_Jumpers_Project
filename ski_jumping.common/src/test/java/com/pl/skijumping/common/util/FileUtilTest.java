package com.pl.skijumping.common.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileUtilTest {

    private static final String TEST_FILE = "testFile.txt";
    private static final String SECOND_TEST_FILE = "secondTestFile.txt";
    private static final String THIRD_TEST_FILE = "thirdTestFile.txt";
    private static final String TEST_DIRECTORY = "testDirectory";
    private static final String TEST_PATH = File.separator + "testDirectory" + File.separator + "testFile.txt";

    @Test
    public void getResourcePathTest() throws IOException {
        Path resourcePath = FileUtil.getResourcePath();
        assert resourcePath != null;
        Path pathToFileInResource = Paths.get(resourcePath.toString(), TEST_FILE);
        Assertions.assertThat(pathToFileInResource.toFile().createNewFile()).isTrue();
        Assertions.assertThat(pathToFileInResource.toFile().isFile()).isTrue();

        Files.delete(pathToFileInResource);
    }

    @Test
    public void getResourceTest() throws IOException {
        String resourcePath = FileUtil.getResource();
        assert resourcePath != null;
        Path pathToFileInResource = Paths.get(resourcePath, TEST_FILE);
        Assertions.assertThat(pathToFileInResource.toFile().createNewFile()).isTrue();
        Assertions.assertThat(pathToFileInResource.toFile().isFile()).isTrue();

        Files.delete(pathToFileInResource);
    }

    @Test
    public void getFileWhenNullTest() {
        Optional<File> file = FileUtil.getFile("");
        Assertions.assertThat(file.isPresent()).isFalse();
        Assertions.assertThat(file).isEqualTo(Optional.empty());
    }

    @Test
    public void getFileTest() throws IOException {
        Path directories = Files.createDirectories(Paths.get(FileUtil.getResource(), TEST_DIRECTORY));
        Path file = Files.createFile(Paths.get(directories.toString(), TEST_FILE));

        Optional<File> actualFile = FileUtil.getFile(FileUtil.getResource() + TEST_PATH);
        Assertions.assertThat(actualFile.isPresent()).isTrue();
        Assertions.assertThat(actualFile.get()).isEqualTo(file.toFile());

        file.toFile().delete();
        directories.toFile().delete();
    }

    @Test
    public void createDirectoryTest() {
        Path directoryPath = FileUtil.createDirectory(TEST_DIRECTORY);
        Assertions.assertThat(directoryPath).isNotNull();

        Optional<File> expectedDirectory = FileUtil.getFile(FileUtil.getResourcePath() + File.separator + TEST_DIRECTORY);
        Assertions.assertThat(expectedDirectory.isPresent()).isTrue();
        Assertions.assertThat(directoryPath.toFile()).isEqualTo(expectedDirectory.get());
        Assertions.assertThat(directoryPath.toFile().isDirectory()).isTrue();

        directoryPath.toFile().delete();
    }

    @Test
    public void createDirectoryWhenNullTest() {
        Path directory = FileUtil.createDirectory(null);
        Assertions.assertThat(directory).isNull();
    }

    @Test
    public void createFileTest() {
        Path resourcePath = FileUtil.getResourcePath();
        Path createdFile = FileUtil.createFile(resourcePath, TEST_FILE);
        Assertions.assertThat(createdFile).isNotNull();
        Assertions.assertThat(createdFile.toFile().exists()).isTrue();
        Assertions.assertThat(createdFile.toFile().isFile()).isTrue();

        Path expectedPath = Paths.get(resourcePath + File.separator + TEST_FILE);
        Assertions.assertThat(createdFile).isEqualTo(expectedPath);

        createdFile.toFile().delete();
    }

    @Test
    public void createFileWhenNullTest() {
        Path createdFile = FileUtil.createFile(null);
        Assertions.assertThat(createdFile).isNull();
    }

    @Test
    public void getFilesFromPath() {
        Path directory = FileUtil.createDirectory(TEST_DIRECTORY);
        Path file = FileUtil.createFile(directory, TEST_FILE);
        Path secondFile = FileUtil.createFile(directory, SECOND_TEST_FILE);
        Path thirdFile = FileUtil.createFile(directory, THIRD_TEST_FILE);

        List<File> actualFile = FileUtil.getFiles(directory.toString() + File.separator);
        Assertions.assertThat(actualFile).isNotNull();
        Assertions.assertThat(actualFile).isNotEmpty();
        Assertions.assertThat(actualFile).hasSize(3);
        Assertions.assertThat(actualFile).containsAll(Arrays.asList(file.toFile(), secondFile.toFile(), thirdFile.toFile()));

        secondFile.toFile().delete();
        thirdFile.toFile().delete();
        file.toFile().delete();
        directory.toFile().delete();
    }

    @Test
    public void getFilesFromString() {
        Path directory = FileUtil.createDirectory(TEST_DIRECTORY);
        Path file = FileUtil.createFile(directory, TEST_FILE);
        Path secondFile = FileUtil.createFile(directory, SECOND_TEST_FILE);
        Path thirdFile = FileUtil.createFile(directory, THIRD_TEST_FILE);

        List<File> actualFile = FileUtil.getFiles(Paths.get(directory.toString() + File.separator));
        Assertions.assertThat(actualFile).isNotNull();
        Assertions.assertThat(actualFile).isNotEmpty();
        Assertions.assertThat(actualFile).hasSize(3);
        Assertions.assertThat(actualFile).containsAll(Arrays.asList(file.toFile(), secondFile.toFile(), thirdFile.toFile()));

        secondFile.toFile().delete();
        thirdFile.toFile().delete();
        file.toFile().delete();
        directory.toFile().delete();
    }
}
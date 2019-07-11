package com.pl.skijumping.common.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileUtilTest {

    private static final String TEST_FILE = "testFile.txt";
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
}
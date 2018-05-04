package com.pl.skijumping.common.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileUtilTest {

    private static final String TEST_FILE = "testFile.txt";
    private static final String TEST_DIRECTORY = "testDirectory";
    private static final String TEST_PATH = "testDirectory" + File.separator + "testFile.txt";

    @Test
    public void getResourcePathTest() throws IOException {
        String resourcePath = FileUtil.getResourcePath();
        File file = new File(resourcePath + File.separator + TEST_FILE);
        Assertions.assertThat(file.createNewFile()).isTrue();
        Assertions.assertThat(file.isFile()).isTrue();

        file.delete();
    }

    @Test
    public void getFileWhenNullTest() throws IOException {
        Optional<File> file = FileUtil.getFile(null);
        Assertions.assertThat(file.isPresent()).isFalse();
        Assertions.assertThat(file).isEqualTo(Optional.empty());
    }

    @Test
    public void getFileTest() throws IOException {
        String resourcePath = FileUtil.getResourcePath();
        File directory = new File(resourcePath + File.separator + TEST_DIRECTORY);
        directory.mkdir();
        File file = new File(directory.getPath() + File.separator + TEST_FILE);
        file.createNewFile();

        Optional<File> actualFile = FileUtil.getFile(TEST_PATH);
        Assertions.assertThat(actualFile.isPresent()).isTrue();
        Assertions.assertThat(actualFile.get()).isEqualTo(file);

        file.delete();
        directory.delete();
    }

    @Test
    public void createDirectoryTest() throws IOException {
        boolean isCreate = FileUtil.createDirectory(TEST_DIRECTORY);
        Assertions.assertThat(isCreate).isTrue();
        String resourcePath = FileUtil.getResourcePath();
        File file = new File(resourcePath + File.separator + TEST_DIRECTORY);
        Assertions.assertThat(file.exists()).isTrue();
        Assertions.assertThat(file.isDirectory()).isTrue();

        file.delete();
    }

    @Test
    public void createDirectoryWhenNullTest() {
        boolean isCreate = FileUtil.createDirectory(null);
        Assertions.assertThat(isCreate).isFalse();
    }

    @Test
    public void createFileTest() throws IOException {
        String resourcePath = FileUtil.getResourcePath();
        boolean isCreate = FileUtil.createFile(TEST_FILE);
        Assertions.assertThat(isCreate).isTrue();
        File file = new File(resourcePath + File.separator + TEST_FILE);
        Assertions.assertThat(file.exists()).isTrue();
        Assertions.assertThat(file.isFile()).isTrue();

        file.delete();
    }

    @Test
    public void createFileWhenNullTest() {
        boolean isCreate = FileUtil.createFile(null);
        Assertions.assertThat(isCreate).isFalse();
    }
}
package com.pl.skijumping.batch.dataimportjob.dataimport;

import com.pl.skijumping.batch.util.FileScannerConst;
import com.pl.skijumping.common.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

public class FilePreparationTest {
    private static final String directoryName = "testDir";
    private static final String fileName = "fileName.txt";

    @Test
    public void testPrepareDirectoryWhenNotExist() {
        Path path = FileUtil.getPath(directoryName);
        Assertions.assertThat(path.toFile().exists()).isFalse();

        Path directory = FilePreparation.prepareDirectory(directoryName);
        Assertions.assertThat(directory.toFile().exists()).isTrue();
        directory.toFile().delete();
    }

    @Test
    public void testPrepareDirectoryWhenExist() {
        Path path = FileUtil.createDirectory(directoryName);
        Assertions.assertThat(path.toFile().exists()).isTrue();

        Path directory = FilePreparation.prepareDirectory(directoryName);
        Assertions.assertThat(directory.toFile().exists()).isTrue();
        directory.toFile().delete();
    }

    @Test
    public void testPrepareFileWhenNotExist() throws IOException {
        Path path = FileUtil.getPath(FileUtil.getResource(), directoryName, fileName);
        Assertions.assertThat(path.toFile().exists()).isFalse();

        Path directoryPath = FileUtil.createDirectory(directoryName);
        Path filePath = FilePreparation.prepareFile(fileName, directoryPath);
        Assertions.assertThat(filePath.toFile().exists()).isTrue();

        filePath.toFile().delete();
        directoryPath.toFile().delete();
    }

    @Test
    public void testPrepareFileWhenExist() throws IOException {
        Path directoryPath = FileUtil.createDirectory(directoryName);
        Path path = FileUtil.getPath(FileUtil.getResource(), directoryName, FileScannerConst.FILE_DATA_IMPORT + fileName);
        path.toFile().createNewFile();
        Assertions.assertThat(path.toFile().exists()).isTrue();

        Path filePath = FilePreparation.prepareFile(fileName, directoryPath);
        Assertions.assertThat(filePath.toFile().exists()).isTrue();

        filePath.toFile().delete();
        directoryPath.toFile().delete();
    }
}
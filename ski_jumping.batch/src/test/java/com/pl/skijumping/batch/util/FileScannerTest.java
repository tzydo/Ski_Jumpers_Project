package com.pl.skijumping.batch.util;

import com.pl.skijumping.common.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FileScannerTest {

    @Test
    public void getPathListTest() throws IOException {
        Path directory = FileUtil.createDirectory("testDir");
        Path firstFile = FileUtil.createFile(directory, FileScannerConst.FILE_DATA_IMPORT + "1.txt");
        Path secondFile = FileUtil.createFile(directory, FileScannerConst.FILE_DATA_IMPORT + "2.txt");
        Path thirdFile = FileUtil.createFile(directory, FileScannerConst.FILE_EVENT_ID + "3.txt");
        Path fourthFile = FileUtil.createFile(directory, "4.txt");

        Set<Path> pathList = FileScanner.getPathList(FileScannerConst.FILE_DATA_IMPORT, directory);
        Assertions.assertThat(pathList).isNotEmpty();
        Assertions.assertThat(pathList).hasSize(2);
        Set expectedPaths = new HashSet();
        expectedPaths.add(firstFile);
        expectedPaths.add(secondFile);
        Assertions.assertThat(pathList).containsAll(expectedPaths);

        Files.delete(firstFile);
        Files.delete(secondFile);
        Files.delete(thirdFile);
        Files.delete(fourthFile);
        Files.delete(directory);
    }
}
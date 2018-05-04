package com.pl.skijumping;

import com.pl.skijumping.exception.InternalServiceException;
import com.pl.skijumping.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileSystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SkiJumpingDataService.class)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class SkiJumpingDataServiceTest {
    private static final String TMP_DIRECTORY = "tmp";
    private static final String TMP_FILE_PATH = TMP_DIRECTORY + File.separator + "SkiJumping.txt";
    private static final String SECOND_TMP_FILE_PATH = TMP_DIRECTORY + File.separator + "SecondSkiJumping.txt";
    private final String host = "http://www.fis-ski.com/ski-jumping/events-and-places/results/";

    @After
    public void removeFile() throws IOException {
        FileSystemUtils.deleteRecursively(FileUtil.getFile(TMP_DIRECTORY).get());
    }

    @Test
    public void getDataTest() throws IOException, InternalServiceException {
        SkiJumpingDataService skiJumpingDataService = new SkiJumpingDataService(host, TMP_DIRECTORY, TMP_FILE_PATH);
        String synchronize = skiJumpingDataService.synchronizeData();

        Assertions.assertThat(synchronize).isNotEmpty();
        Path filePath = Paths.get(synchronize);
        File file = new File(filePath.toUri());
        Assertions.assertThat(file.exists()).isTrue();

        BufferedReader reader = Files.newBufferedReader(filePath);
        List<String> readLinesList = reader.lines().collect(Collectors.toList());
        reader.close();
        Assertions.assertThat(readLinesList.isEmpty()).isFalse();
        Assertions.assertThat(readLinesList.size()).isGreaterThan(0);
    }

    @Test
    public void getDataWhenFileExistTest() throws IOException, InternalServiceException {
        FileUtil.createFile(SECOND_TMP_FILE_PATH);
        SkiJumpingDataService skiJumpingDataService = new SkiJumpingDataService(host, TMP_DIRECTORY, SECOND_TMP_FILE_PATH);
        String path = skiJumpingDataService.synchronizeData();
        Path filePath = Paths.get(path);
        Assertions.assertThat(Files.exists(filePath)).isTrue();
        List<String> readLines = Files.lines(filePath).collect(Collectors.toList());
        Assertions.assertThat(readLines.isEmpty()).isFalse();
    }
}
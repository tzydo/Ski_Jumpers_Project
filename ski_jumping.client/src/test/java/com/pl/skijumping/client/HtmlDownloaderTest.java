package com.pl.skijumping.client;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.common.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileSystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class HtmlDownloaderTest {
    private static final String TMP_DIRECTORY = "tmp";
    private static final String TMP_FILE_PATH = TMP_DIRECTORY + File.separator + "SkiJumping.txt";
    private final String host = "http://www.fis-ski.com/ski-jumping/events-and-places/results/";

    @After
    public void removeFile() {
        Optional<File> file = FileUtil.getFile(TMP_DIRECTORY);
        file.ifPresent(FileSystemUtils::deleteRecursively);
    }

    @Test
    public void getDataTest() throws IOException, InternalServiceException {
        FileUtil.createDirectory(TMP_DIRECTORY);
        FileUtil.createFile(TMP_FILE_PATH);
        HtmlDownloader htmlDownloader = new HtmlDownloader(TMP_FILE_PATH, host);
        String synchronize = htmlDownloader.downloadSource();

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
    public void getDataWhenFileDoesNotExistTest() throws IOException, InternalServiceException {
        HtmlDownloader htmlDownloader = new HtmlDownloader(TMP_FILE_PATH, host);
        Assertions.assertThat(htmlDownloader.downloadSource()).isEqualTo(null);
    }

    @Test
    public void getDataWhenNullHostTest() {
        FileUtil.createDirectory(TMP_DIRECTORY);
        FileUtil.createFile(TMP_FILE_PATH);
        HtmlDownloader htmlDownloader = new HtmlDownloader(TMP_FILE_PATH, null);

        Throwable throwable = Assertions.catchThrowable(() -> htmlDownloader.downloadSource());

        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
    }

    @Test
    public void getDataWhenIncorrectHostTest() {
        FileUtil.createDirectory(TMP_DIRECTORY);
        FileUtil.createFile(TMP_FILE_PATH);
        HtmlDownloader htmlDownloader = new HtmlDownloader(TMP_FILE_PATH, "testValue");

        Throwable throwable = Assertions.catchThrowable(() -> htmlDownloader.downloadSource());

        Assertions.assertThat(throwable).isInstanceOf(IOException.class);
    }
}
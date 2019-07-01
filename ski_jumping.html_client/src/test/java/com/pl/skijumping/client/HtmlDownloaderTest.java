//package com.pl.skijumping.client;
//
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.common.util.FileUtil;
//import org.assertj.core.api.Assertions;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.FileSystemUtils;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("test")
//@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
//public class HtmlDownloaderTest {
//    private static final String TMP_DIRECTORY = "tmp";
//    private static final String FILE_NAME = "SkiJumping.txt";
//    private static final String HOST = "https://www.fis-ski.com/DB/ski-jumping/calendar-results.html";
//    private static final String TMP_FILE_PATH = "tmp" + File.separator + "test.txt";
//
//    @After
//    public void removeFile() throws IOException {
//        Path directoryPath = FileUtil.getPath(FileUtil.getResource(), TMP_DIRECTORY);
//        if (directoryPath.toFile().exists()) {
//            List<File> file = FileUtil.getFiles(directoryPath);
//            file.forEach(FileSystemUtils::deleteRecursively);
//            directoryPath.toFile().delete();
//        }
//    }
//
//    @Test
//    public void getDataTest() throws IOException, InternalServiceException {
//        Path directory = FileUtil.createDirectory(TMP_DIRECTORY);
//        Path pathToFile = FileUtil.createFile(directory, FILE_NAME);
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//        Path downloadSource = htmlDownloader.downloadSource(pathToFile, HOST);
//
//        Assertions.assertThat(downloadSource).isNotNull();
//        Assertions.assertThat(downloadSource.toFile().exists()).isTrue();
//
//        BufferedReader reader = Files.newBufferedReader(downloadSource);
//        List<String> readLinesList = reader.lines().collect(Collectors.toList());
//        reader.close();
//        Assertions.assertThat(readLinesList.isEmpty()).isFalse();
//        Assertions.assertThat(readLinesList.size()).isGreaterThan(0);
//    }
//
//    @Test
//    public void getDataWhenFileDoesNotExistTest() throws IOException, InternalServiceException {
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//        Assertions.assertThat(htmlDownloader.downloadSource(Paths.get(TMP_FILE_PATH), HOST)).isEqualTo(null);
//    }
//
//    @Test
//    public void getDataWhenNullHostTest() {
//        Path directory = FileUtil.createDirectory(TMP_DIRECTORY);
//        Path pathToFile = FileUtil.createFile(directory, FILE_NAME);
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//
//        Throwable throwable = Assertions.catchThrowable(() -> htmlDownloader.downloadSource(pathToFile, null));
//
//        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
//    }
//
//    @Test
//    public void getDataWhenIncorrectHostTest() {
//        Path directory = FileUtil.createDirectory(TMP_DIRECTORY);
//        Path pathToFile = FileUtil.createFile(directory, FILE_NAME);
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//
//        Throwable throwable = Assertions.catchThrowable(() -> htmlDownloader.downloadSource(pathToFile, "testValue"));
//
//        Assertions.assertThat(throwable).isInstanceOf(IOException.class);
//    }
//
//    @Test
//    public void downloadToStringTest() throws IOException, InternalServiceException {
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//        String source = htmlDownloader.downloadToString(HOST);
//        Assertions.assertThat(source).isNotNull();
//        Assertions.assertThat(source).isNotEmpty();
//    }
//
//    @Test
//    public void downloadToStringWhenNullTest() throws IOException, InternalServiceException {
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//        String source = htmlDownloader.downloadToString(null);
//        Assertions.assertThat(source).isNull();
//    }
//
//    @Test
//    public void downloadToStringWhenEmptyTest() throws IOException, InternalServiceException {
//        HtmlDownloader htmlDownloader = new HtmlDownloader();
//        String source = htmlDownloader.downloadToString("");
//        Assertions.assertThat(source).isNull();
//    }
//}
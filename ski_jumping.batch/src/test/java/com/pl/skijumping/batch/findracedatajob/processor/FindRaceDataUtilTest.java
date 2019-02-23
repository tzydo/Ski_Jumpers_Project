package com.pl.skijumping.batch.findracedatajob.processor;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FindRaceDataUtilTest {

    @Test
    public void getYearFromFilePathTest() {
        Path path = Paths.get("test/test_2018_999.txt");
        String yearFromFilePath = FindRaceDataUtil.getYearFromFilePath(path);
        Assertions.assertThat(yearFromFilePath).isNotNull();
        Assertions.assertThat(yearFromFilePath).isEqualTo("2018");
    }

    @Test
    public void getYearFromFilePathWhenNotContainTest() {
        Path path = Paths.get("test/test999.txt");
        String yearFromFilePath = FindRaceDataUtil.getYearFromFilePath(path);
        Assertions.assertThat(yearFromFilePath).isNull();
    }

    @Test
    public void getEventIdFromFilePathTest() {
        Path path = Paths.get("test/test_2018_999.txt");
        String yearFromFilePath = FindRaceDataUtil.getEventIdFromFilePath(path);
        Assertions.assertThat(yearFromFilePath).isNotNull();
        Assertions.assertThat(yearFromFilePath).isEqualTo("999");
    }

    @Test
    public void getEventIdFromFilePathNotContainTest() {
        Path path = Paths.get("test/test_2018.txt");
        String yearFromFilePath = FindRaceDataUtil.getYearFromFilePath(path);
        Assertions.assertThat(yearFromFilePath).isNull();
    }

    @Test
    public void gGenerateMonthNumberTest() {
        Integer monthNumber = FindRaceDataUtil.generateMonthNumber("02");
        Assertions.assertThat(monthNumber).isEqualTo(2);
    }

    @Test
    public void GenerateMonthNumberWhenDoubleSizeTest() {
        Integer monthNumber = FindRaceDataUtil.generateMonthNumber("12");
        Assertions.assertThat(monthNumber).isEqualTo(12);
    }

    @Test
    public void generateMonthNumberWhenNullTest() {
        Integer monthNumber = FindRaceDataUtil.generateMonthNumber(null);
        Assertions.assertThat(monthNumber).isNull();
    }

    @Test
    public void generateDateTest() {
        String date = "27 Dec";
        LocalDate actualDate = FindRaceDataUtil.generateDate(date, "2018");
        LocalDate expectedDate = LocalDate.of(2018, 12, 27);
        Assertions.assertThat(actualDate).isEqualTo(expectedDate);
    }
}
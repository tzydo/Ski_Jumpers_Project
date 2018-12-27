package com.pl.skijumping.batch.dataimportjob;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataImporterUtilTest {

    @Test
    public void testGenerateSeasonCodeByPreviousMonthsWhenIncorrectYear() {
        List<String> seasonCode = DataImporterUtil.generateSeasonCodeByPreviousMonths(123, 22, 4, "test");
        Assertions.assertThat(seasonCode).isEmpty();
        Assertions.assertThat(seasonCode).isEqualTo(new ArrayList<>());
    }

    @Test
    public void testSeasonMonthAndCodeGenerator() {
        String host = "test";
        List<String> expectedList = Arrays.asList(
                generate(2018, "04", host),
                generate(2018, "03", host)
        );

        List<String> monthAndCodeGenerator = DataImporterUtil.generateSeasonCodeByPreviousMonths(2018, 4, 2, host);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(2);
        Assertions.assertThat(monthAndCodeGenerator).isEqualTo(expectedList);
    }

    @Test
    public void testSeasonMonthAndCodeGeneratorByPreviousYear() {
        String host = "test";
        List<String> expectedList = Arrays.asList(
                generate(2018, "01", host),
                generate(2017, "12", host)
        );

        List<String> monthAndCodeGenerator = DataImporterUtil.generateSeasonCodeByPreviousMonths(2018, 1, 2, host);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(2);
        Assertions.assertThat(monthAndCodeGenerator.containsAll(expectedList)).isTrue();
    }

    @Test
    public void testGetNameFromHost() {
        String testWord = DataImporterConst.SEASON_MONTH + "test";
        String actualWord = DataImporterUtil.getFileNameFromHost(testWord);
        Assertions.assertThat(actualWord).isEqualTo("test" + DataImporterConst.FILE_TYPE);
    }

    @Test
    public void testGetNameFromNullHost() {
        String actualWord = DataImporterUtil.getFileNameFromHost(null);
        Assertions.assertThat(actualWord).isEqualTo(DataImporterConst.DEFAULT_FILE_NAME);
    }

    private String generate(Integer year, String month, String host) {
        return String.format("%s%s%d%s%s-%d", host, DataImporterConst.SEASON_CODE, year + 1, DataImporterConst.SEASON_MONTH, month, year);
    }
}
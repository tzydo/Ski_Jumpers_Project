package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.dto.Months;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataImporterUtilTest {

    @Test
    public void testYearGenerator() {
        int currentYear = LocalDate.now().getYear();
        List<Integer> yearList = DataImporterUtil.yearGenerator(3);
        Assertions.assertThat(yearList).isNotNull();
        Assertions.assertThat(yearList).isNotEmpty();
        Assertions.assertThat(yearList).hasSize(4);
        Assertions.assertThat(yearList).containsAll(Arrays.asList(currentYear, currentYear-1, currentYear-2, currentYear-3));
    }

    @Test
    public void testYearGeneratorWhenNull() {
        int currentYear = LocalDate.now().getYear();
        List<Integer> yearList = DataImporterUtil.yearGenerator(null);
        Assertions.assertThat(yearList).isNotNull();
        Assertions.assertThat(yearList).isNotEmpty();
        Assertions.assertThat(yearList).hasSize(3);
        Assertions.assertThat(yearList).containsAll(Arrays.asList(currentYear, currentYear-1, currentYear-2));
    }

    @Test
    public void testYearGeneratorWhenYearOutOfRange() {
        int currentYear = LocalDate.now().getYear();
        List<Integer> yearList = DataImporterUtil.yearGenerator(999);
        Assertions.assertThat(yearList).isNotNull();
        Assertions.assertThat(yearList).isNotEmpty();
        Assertions.assertThat(yearList).hasSize(3);
        Assertions.assertThat(yearList).containsAll(Arrays.asList(currentYear, currentYear-1, currentYear-2));
    }

    @Test
    public void testSeasonMonthAndCodeGenerator() {
        String host = "test";
        List<String> expectedList = generate(LocalDate.now().getYear(), host);

        List<String> monthAndCodeGenerator = DataImporterUtil.seasonMonthAndCodeGenerator(LocalDate.now().getYear(), host);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(12);
        Assertions.assertThat(monthAndCodeGenerator).isEqualTo(expectedList);
    }

    @Test
    public void testSeasonMonthAndCodeGeneratorByPreviousYear() {
        String host = "test";
        List<String> expectedList = generate(LocalDate.now().getYear(), host);
        expectedList.addAll(generate(LocalDate.now().getYear()-1, host));
        expectedList.addAll(generate(LocalDate.now().getYear()-2, host));

        List<String> monthAndCodeGenerator = DataImporterUtil.generateSeasonMonthAndCodeByPreviousYear(2, host);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(36);
        Assertions.assertThat(monthAndCodeGenerator.containsAll(expectedList)).isTrue();
    }

    @Test
    public void testGetNameFromHost() {
        String testWord = DataImporterConst.SEASON_MONTH + "test";
        String actualWord = DataImporterUtil.getFileNameFromHost(testWord);
        Assertions.assertThat(actualWord).isEqualTo("test"+ DataImporterConst.FILE_TYPE);
    }

    @Test
    public void testGetNameFromNullHost() {
        String actualWord = DataImporterUtil.getFileNameFromHost(null);
        Assertions.assertThat(actualWord).isEqualTo(DataImporterConst.DEFAULT_FILE_NAME);
    }

    private List<String> generate(Integer year, String host) {
        return Months.getValueList().stream().map(months -> String.format("%s%s%d%s%s-%d", host, DataImporterConst.SEASON_CODE, year + 1, DataImporterConst.SEASON_MONTH, months, year)).collect(Collectors.toList());
    }
}
package com.pl.skijumping.batch.dataimportjob;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataImporterUtilTest {

    @Test
    public void testGenerateSeasonCodeByPreviousMonthsWhenIncorrectYear() {
        List<LocalDate> localDates = DataImporterUtil.generateListOfDates(123, 22, 4);
        Assertions.assertThat(localDates).isEmpty();
        Assertions.assertThat(localDates).isEqualTo(new ArrayList<>());
    }

    @Test
    public void testSeasonMonthGenerator() {
        List<LocalDate> localDates = DataImporterUtil.generateListOfDates(2018, 4, 2);
        Assertions.assertThat(localDates).isNotNull();
        Assertions.assertThat(localDates).hasSize(2);
        Assertions.assertThat(localDates).containsAll(Arrays.asList(LocalDate.of(2018, 4, 1), LocalDate.of(2018, 3, 1)));
    }

    @Test
    public void testSeasonMonthGeneratorByPreviousYear() {
        List<LocalDate> monthAndCodeGenerator = DataImporterUtil.generateListOfDates(2018, 1, 2);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(2);
        Assertions.assertThat(monthAndCodeGenerator).containsAll(Arrays.asList(LocalDate.of(2018, 1, 1), LocalDate.of(2017, 12, 1)));
    }

    @Test
    public void testSeasonMonthGeneratorWhenDecember() {
        List<LocalDate> monthAndCodeGenerator = DataImporterUtil.generateListOfDates(2018, 12, 2);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).hasSize(2);
        Assertions.assertThat(monthAndCodeGenerator).containsAll(Arrays.asList(LocalDate.of(2018, 12, 1), LocalDate.of(2018, 11, 1)));
    }

    @Test
    public void testSeasonMonthGeneratorWhenCurrentPreviousMonthsNull() {
        List<LocalDate> monthAndCodeGenerator = DataImporterUtil.generateListOfDates(2018, 12, null);
        Assertions.assertThat(monthAndCodeGenerator).isNotNull();
        Assertions.assertThat(monthAndCodeGenerator).isEqualTo(new ArrayList<>());
    }
}
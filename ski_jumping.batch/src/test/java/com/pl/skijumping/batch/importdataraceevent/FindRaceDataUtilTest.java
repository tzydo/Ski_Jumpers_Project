package com.pl.skijumping.batch.importdataraceevent;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;

public class FindRaceDataUtilTest {

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
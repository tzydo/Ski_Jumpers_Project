package com.pl.skijumping.domain.model;

import java.util.ArrayList;
import java.util.List;

public enum Months {

    JAN("01"),
    FEB("02"),
    MAR("03"),
    APR("04"),
    MAY("05"),
    JUN("06"),
    JUL("07"),
    AUG("08"),
    SEP("09"),
    OCT("10"),
    NOV("11"),
    DEC("12");

    private String monthNumber;

    Months(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public static String getValue(String name) {
        String value = null;
        for (Months months : values()) {
            if (months.name().contains(name)) {
                value = months.getMonthNumber();
                break;
            }
        }

        return value;
    }

    public static List<String> getValueList() {
        List<String> monthList = new ArrayList<>();
        for (Months month : values()) {
            monthList.add(month.getMonthNumber());
        }

        return monthList;
    }

    private String getMonthNumber() {
        return monthNumber;
    }
}

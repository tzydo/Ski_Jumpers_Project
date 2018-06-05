package com.pl.skijumping.dto;

public enum Months {
    JAN(1),
    FEB(2),
    MAR(3),
    APR(4),
    MAY(5),
    JUN(6),
    JUL(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);

    private Integer monthNumber;

    private Months(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public static Integer getValue(String name) {
        Integer value = null;
        for(Months months: values()) {
            if(months.name().equals(name)){
                value = months.getMonthNumber();
                break;
            }
        }

        return value;
    }

    private Integer getMonthNumber() {
        return monthNumber;
    }
}

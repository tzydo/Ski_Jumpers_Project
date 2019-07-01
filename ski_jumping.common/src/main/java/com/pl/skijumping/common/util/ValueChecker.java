package com.pl.skijumping.common.util;

public class ValueChecker {
    private ValueChecker() {
//
    }

    public static <T> T isNull(T value, String errorMessage) {
        if (value != null) {
            return value;
        }
        throw new IllegalStateException(errorMessage);
    }
}

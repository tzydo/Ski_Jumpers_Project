package com.pl.skijumping.domain.model;

import java.util.Arrays;

public enum Gender {
    MALE("male", "MALE", "M", "m"),
    FEMALE("female", "F", "f", "L", "l");

    private String[] value;

    Gender(String... value) {
        this.value = value;
    }

    public static Gender getValue(String text) {
        if (text == null) {
            return null;
        }

        for (Gender gender : values()) {
            if (Arrays.asList(gender.value).contains(text)) {
                return gender;
            }
        }
        return null;
    }
}

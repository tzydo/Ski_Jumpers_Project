package com.pl.skijumping.domain.model;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE("male", "m"),
    FEMALE("female", "female", "f", "l"),
    MIXED_TEAM("a", "mixed_team"),
    UNDEFINED("undefined");

    private List<String> value;

    Gender(String... value) {
        this.value = Arrays.asList(value);
    }

    public static Gender getEnum(String text) {
        if (text == null) {
            return null;
        }

        for (Gender gender : values()) {
            if (gender.value.contains(text.toLowerCase().trim())) {
                return gender;
            }
        }
        return Gender.UNDEFINED;
    }
}

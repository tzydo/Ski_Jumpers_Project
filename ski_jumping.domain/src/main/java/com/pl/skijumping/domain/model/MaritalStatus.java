package com.pl.skijumping.domain.model;

import java.util.Arrays;
import java.util.List;

public enum MaritalStatus {
    MARRIED("married"),
    SINGLE("single"),
    IN_RELATIONSHIP("in_relationship", "in a relationship"),
    UNDEFINED("undefined");

    private List<String> text;

    MaritalStatus(String... text) {
        this.text = Arrays.asList(text);
    }

    public static MaritalStatus getEnum(String text) {
        if (text == null) {
            return null;
        }

        for (MaritalStatus maritalStatus : values()) {
            if (maritalStatus.text.contains(text.toLowerCase().trim())) {
                return maritalStatus;
            }
        }
        return MaritalStatus.UNDEFINED;
    }
}

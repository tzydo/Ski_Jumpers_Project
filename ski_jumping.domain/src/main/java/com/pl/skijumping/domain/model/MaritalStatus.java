package com.pl.skijumping.domain.model;

public enum MaritalStatus {
    MARRIED("MARRIED"),
    SINGLE("SINGLE");

    private String text;

    private MaritalStatus(String text) {
        this.text = text;
    }

    public static MaritalStatus getStatus(String text) {
        MaritalStatus value = null;
        for (MaritalStatus maritalStatus : values()) {
            if (maritalStatus.text.equalsIgnoreCase(text)) {
                value = maritalStatus;
            }
        }
        return value;
    }
}

package com.pl.skijumping.dto;

public enum MessageProperties {
    TOURNAMENT_YEAR("TOURNAMENT_YEAR"),
    EVENT_ID("EVENT_ID"),

    DARA_RACE_ID("DARA_RACE_ID"),
    COMPETITION_TYPE("COMPETITION_TYPE"),
    JUMP_RESULT("JUMP_RESULT"),

    DOWNLOAD_SOURCE_URL("DOWNLOAD_SOURCE_URL"),
    FILE_NAME("FILE_NAEM"),
    DESTINATION_TARGET("DESTINATION_TARGET");

    private String value;

    MessageProperties(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

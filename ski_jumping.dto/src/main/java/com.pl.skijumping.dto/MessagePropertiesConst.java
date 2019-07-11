package com.pl.skijumping.dto;

public enum MessagePropertiesConst {
    EVENT_ID("EVENT_ID"),
    SEASON_CODE("SEASON_CODE"),
    DARA_RACE_ID("DARA_RACE_ID"),
    COMPETITION_TYPE("COMPETITION_TYPE"),
    JUMP_RESULT("JUMP_RESULT"),
    DOWNLOAD_SOURCE_URL("DOWNLOAD_SOURCE_URL"),
    FILE_NAME("FILE_NAME"),
    DESTINATION_TARGET("DESTINATION_TARGET");

    private String value;

    MessagePropertiesConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

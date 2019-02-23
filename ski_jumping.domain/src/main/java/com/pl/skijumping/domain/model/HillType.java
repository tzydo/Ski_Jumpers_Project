package com.pl.skijumping.domain.model;


public enum HillType {
    SMALL_HILL("SMALL_HILL"),
    MEDIUM_HILL("MEDIUM_HILL"),
    NORMAL_HILL("NORMAL_HILL"),
    LARGE_HILL("LARGE_HILL"),
    SKI_FLYING_HILL("SKI_FLYING_HILL");

    private String hillType;

    HillType(String hillType) {
        this.hillType = hillType;
    }

    public String getHillType() {
        return hillType;
    }
}

package com.pl.skijumping.domain.model;

public enum Gender {
    MALE ("male"),
    FEMALE ("female");

    private String text;

    private Gender(String text) {
        this.text = text;
    }

    public static Gender getValue(String text) {
        Gender value = null;
        for(Gender gender: values()) {
            if(gender.text.equalsIgnoreCase(text)) {
                value = gender;
            }
        }
        return value;
    }
}

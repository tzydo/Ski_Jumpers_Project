package com.pl.skijumping.domain.entity;

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

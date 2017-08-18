package com.pl.basic.page.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class Title {
    private String title;
    private String name;
    private String lastName;
    private String email;
    @Override
    public String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}

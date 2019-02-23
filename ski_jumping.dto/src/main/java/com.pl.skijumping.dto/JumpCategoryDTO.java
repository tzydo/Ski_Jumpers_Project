package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JumpCategoryDTO {

    private Integer id;
    private String name;
    private String shortName;


    public JumpCategoryDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public JumpCategoryDTO name(String name) {
        this.name = name;
        return this;
    }

    public JumpCategoryDTO shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }
}

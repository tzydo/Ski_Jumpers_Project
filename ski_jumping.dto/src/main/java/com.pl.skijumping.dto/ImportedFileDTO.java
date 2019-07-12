package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportedFileDTO {
    private Long id;
    private String file;
    private String url;

    public ImportedFileDTO id(Long id) {
        this.id = id;
        return this;
    }

    public ImportedFileDTO file(String file) {
        this.file = file;
        return this;
    }

    public ImportedFileDTO url(String url) {
        this.url = url;
        return this;
    }
}

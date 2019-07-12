package com.pl.skijumping.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imported_file")
public class ImportedFile {
    private Long id;
    private String file;
    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "file", nullable = false)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Column(name = "url", nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportedFile that = (ImportedFile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(file, that.file) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, file, url);
    }

    public ImportedFile id(Long id) {
        this.id = id;
        return this;
    }

    public ImportedFile fileName(String fileName) {
        this.file = fileName;
        return this;
    }

    public ImportedFile url(String url) {
        this.url = url;
        return this;
    }
}

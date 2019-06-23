package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {
    private String filePath;
    private MessageProperties properties;

    public MessageDTO() {
        this.properties = new MessageProperties();
    }

    public MessageDTO filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public MessageDTO properties(Map<String, Object> properties) {
        this.properties.setProperties(properties);
        return this;
    }

    public MessageDTO addProperties(String key, Object value) {
        this.properties.addProperties(key, value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDTO that = (MessageDTO) o;
        return Objects.equals(filePath, that.filePath) &&
                Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, properties);
    }
}

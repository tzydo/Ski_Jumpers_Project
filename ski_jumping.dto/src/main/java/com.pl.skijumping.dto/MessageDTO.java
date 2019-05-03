package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class MessageDTO implements Serializable {
    private String filePath;
    private Map<String, Object> properties;

    public MessageDTO() {
        this.properties = new HashMap<>();
    }

    public MessageDTO filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public MessageDTO properties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public MessageDTO addProperties(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }
}

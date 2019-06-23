package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
public class MessageProperties {
    private Map<String, Object> properties;

    public MessageProperties() {
        this.properties = new HashMap<>();
    }

    public void setProperties(Map<String, Object> value) {
        this.properties = value;
    }

    public void addProperties(String key, Object value) {
        this.properties.put(key, value);
    }

    public String getStringValue(String key) {
        return ObjectParser.toString(this.properties.get(key));
    }

    public Long getLongValue(String key) {
        return ObjectParser.toLong(this.properties.get(key));
    }

    public Double getDoubleValue(String key) {
        return ObjectParser.toDouble(this.properties.get(key));
    }

    public Integer getIntValue(String key) {
        return ObjectParser.toInt(this.properties.get(key));
    }

    public Object getObject(String key) {
        return this.properties.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageProperties that = (MessageProperties) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}

package com.pl.skijumping.dto;

class ObjectParser {
    public static String toString(Object value) {
        if (value instanceof String) {
            return value.toString();
        }
        throw new IllegalStateException(String.format("Cannot cast value: %s to type String.class", value));
    }

    public static Double toDouble(Object value) {
        if (value instanceof Double) {
            return Double.parseDouble(value.toString());
        }
        throw new IllegalStateException(String.format("Cannot cast value: %s to type Double.class", value));
    }

    public static Long toLong(Object value) {
        if ( value instanceof Long || value instanceof Integer) {
            return Long.parseLong(value.toString());
        }
        throw new IllegalStateException(String.format("Cannot cast value: %s to type Long.class", value));
    }


    public static Integer toInt(Object value) {
        if ( value instanceof Integer) {
            return Integer.parseInt(value.toString());
        }
        throw new IllegalStateException(String.format("Cannot cast value: %s to type Integer.class", value));
    }
}

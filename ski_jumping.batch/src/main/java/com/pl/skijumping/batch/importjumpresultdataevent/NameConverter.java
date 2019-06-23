package com.pl.skijumping.batch.importjumpresultdataevent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class NameConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameConverter.class);

    private NameConverter() {
//
    }

    public static String convert(String name) {
        if (name == null) {
            LOGGER.warn("Cannot convert null name");
            return null;
        }

        StringBuilder newName = new StringBuilder();
        String surname = null;
        String[] partOfNameList = name.toLowerCase().split(" ");
        for (String part : partOfNameList) {
            part = StringUtils.capitalize(part);
            if (!partOfNameList[0].equalsIgnoreCase(part)) {
                newName.append(part).append(" ");
            } else {
                surname = part;
            }
        }

        newName.append(surname);
        return newName.toString().trim();
    }
}

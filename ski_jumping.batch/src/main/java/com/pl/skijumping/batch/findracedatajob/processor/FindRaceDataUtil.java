package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.domain.model.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDate;

class FindRaceDataUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindRaceDataUtil.class);
    private static final String PATTERN = "_";

    private FindRaceDataUtil() {
        //
    }

    static String getYearFromFilePath(Path filePath) {
        if (filePath == null) {
            LOGGER.error("Cannot get year from null file path");
            return null;
        }

        String[] words = filePath.getFileName().toString().split(PATTERN);
        if (words != null && words.length == 3) {
            return words[1];
        }
        return null;
    }

    static String getEventIdFromFilePath(Path filePath) {
        if (filePath == null) {
            LOGGER.error("Cannot get event id from null file path");
            return null;
        }

        String[] words = filePath.getFileName().toString().split("_");
        if (words == null || words.length == 0) {
            return null;
        }

        return words[2].split("\\.")[0];
    }

    static Integer generateMonthNumber(String monthValue) {
        if(monthValue == null) {
            LOGGER.error("Cannot generate month number from null");
            return null;
        }

        try {
            if (monthValue.substring(0, 1).equals("0")) {
                return Integer.parseInt(monthValue.substring(1, 2));
            } else {
                return Integer.parseInt(monthValue);
            }
        }catch (NumberFormatException e) {
            return null;
        }
    }

    static LocalDate generateDate(String raceDataDate, String year) {
        if(raceDataDate == null || raceDataDate.isEmpty() || year == null || year.isEmpty()) {
            LOGGER.error("Cannot generate date from empty words");
            return null;
        }

        String[] date = raceDataDate.split(" ");
        if(date.length != 2) {
            LOGGER.error("Incorrect count of parameters in race data generator");
            return null;
        }

        String monthValue = Months.getValue(date[1].toUpperCase());
        Integer monthNumber = generateMonthNumber(monthValue);

        try {
            return LocalDate.of(Integer.parseInt(year), monthNumber, Integer.parseInt(date[0]));
        }catch (NumberFormatException e) {
            return null;
        }
    }
}

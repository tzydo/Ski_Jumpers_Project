package com.pl.skijumping.batch.dataimportjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DataImporterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterUtil.class);

    private DataImporterUtil() {
//
    }

    static List<LocalDate> generateListOfDates(Integer currentYear, Integer currentMonth, Integer countOfPreviousMonths) {
        if (currentYear == null || currentYear.toString().length() < 4 || currentYear.toString().length() > 4) {
            LOGGER.warn("Cannot generate season month and code from incorrect year ");
            return new ArrayList<>();
        }

        if (countOfPreviousMonths == null) {
            LOGGER.warn("Cannot generate season month and code from null ");
            return new ArrayList<>();
        }

        LocalDate currentLocalDate = LocalDate.of(currentYear, currentMonth, 1);
        return IntStream.range(0, countOfPreviousMonths)
                .boxed()
                .sorted(Collections.reverseOrder())
                .map(currentLocalDate::minusMonths)
                .collect(Collectors.toList());
    }
}
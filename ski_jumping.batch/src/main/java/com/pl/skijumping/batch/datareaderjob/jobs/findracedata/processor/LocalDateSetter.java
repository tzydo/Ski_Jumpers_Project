package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.domain.dto.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

class LocalDateSetter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateSetter.class);
    private final List<String> dateList;

    public LocalDateSetter(List<String> dateList) {
        this.dateList = dateList;
    }

    public LocalDate setDate() throws InternalServiceException {
        if (dateList == null || dateList.isEmpty()) {
            LOGGER.error("Not found any value to create date");
            return null;
        }

        if (dateList.size() > 3) {
            throw new InternalServiceException(
                    String.format("Cannot set date from list:%s values exceed the range", dateList));
        }

        Integer day = getValue(dateList, 0);
        Integer month = Months.getValue(dateList.get(1));
        Integer year = getValue(dateList, 2);

        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException | NullPointerException e) {
            LOGGER.error("Cannot set date from values: day = %s, month = %s, year = %s", day, month, year);
            return null;
        }
    }

    private Integer getValue(List<String> dateList, int needValue) {
        if (dateList.size() < needValue) {
            LOGGER.error(String.format("Cannot get %d from list, value exceed the range", needValue));
            return null;
        }

        Integer value = null;
        try {
            value = Integer.parseInt(dateList.get(needValue));
        } catch (NumberFormatException e) {
            LOGGER.error(String.format("Cannot change value from string:%s to integer!", dateList.get(needValue)));
        }
        return value;
    }
}

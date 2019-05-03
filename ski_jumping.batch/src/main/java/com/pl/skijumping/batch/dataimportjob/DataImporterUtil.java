package com.pl.skijumping.batch.dataimportjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class DataImporterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterUtil.class);

    private DataImporterUtil() {
//
    }

    static List<String> generateSeasonCodeByPreviousMonths(Integer currentYear, Integer currentMonth, Integer countOfPreviousMonths, String host) {
        if (currentYear == null || currentYear.toString().length() < 4 || currentYear.toString().length() > 4) {
            LOGGER.warn("Cannot generate season month and code from incorrect year ");
            return new ArrayList<>();
        }

        if (countOfPreviousMonths == null) {
            LOGGER.warn("Cannot generate season month and code from null ");
            return new ArrayList<>();
        }

        if (host == null || host.isEmpty()) {
            LOGGER.warn("Cannot generate season month and code from null host");
            return new ArrayList<>();
        }

        List<String> seasonCodeAndMonthList = new ArrayList<>();
        for (int i = countOfPreviousMonths; i > 0; i--) {
            seasonCodeAndMonthList.add(String.format("%s%s%s%s%s-%d", host, DataImporterConst.SEASON_CODE, currentYear + 1, DataImporterConst.SEASON_MONTH, addPrefixMonth(Integer.toString(currentMonth)), currentYear));
            if (currentMonth == 1) {
                currentMonth = 12;
                currentYear -= 1;
            } else {
                currentMonth -= 1;
            }
        }
        return seasonCodeAndMonthList;
    }

    private static String addPrefixMonth(String month) {
        if (month.length() > 1) {
            return month;
        }

        return DataImporterConst.MONTH_ZERO_PREFIX + month;
    }

    static String getFileNameFromHost(String host) {
        if (host == null || host.isEmpty()) {
            LOGGER.warn("Cannot read file name from null host");
            return DataImporterConst.DEFAULT_FILE_NAME;
        }

        String[] split = host.split(DataImporterConst.SEASON_MONTH);
        return split[1] + DataImporterConst.FILE_TYPE;
    }
}
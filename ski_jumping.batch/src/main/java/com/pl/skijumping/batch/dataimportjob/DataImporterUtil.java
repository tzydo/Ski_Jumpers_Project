package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.dto.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataImporterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterUtil.class);

    private DataImporterUtil() {
//
    }

    public static List<String> generateSeasonMonthAndCodeByPreviousYear(Integer countOfPreviousYears, String host) {
        if (countOfPreviousYears == null) {
            LOGGER.warn("Cannot generate season month and code from null ");
            return new ArrayList<>();
        }

        if (host == null || host.isEmpty()) {
            LOGGER.warn("Cannot generate season month and code from null host");
            return new ArrayList<>();
        }

        return yearGenerator(countOfPreviousYears).stream().map(year-> seasonMonthAndCodeGenerator(year, host)).flatMap(Collection::stream).collect(Collectors.toList());
    }


    static List<String> seasonMonthAndCodeGenerator(Integer currentYear, String host) {
        if (currentYear == null) {
            LOGGER.warn("Cannot generate season month and code from null");
            return new ArrayList<>();
        }

        List<String> seasonCodeAndMonthList = new ArrayList<>();
        Months.getValueList().forEach(month ->
                seasonCodeAndMonthList.add(String.format("%s%s%s%s%s-%d", host, DataImporterConst.SEASON_CODE, currentYear + 1, DataImporterConst.SEASON_MONTH, month, currentYear))
        );
        return seasonCodeAndMonthList;
    }

    static List<Integer> yearGenerator(Integer countOfPreviousYears) {
        if (countOfPreviousYears == null || countOfPreviousYears > DataImporterConst.MAX_PREVIOUS_YEAR || countOfPreviousYears <= 0) {
            LOGGER.warn("Invalid value for count of previous year: {}", countOfPreviousYears);
            countOfPreviousYears = DataImporterConst.DEFAULT_YEAR_TO_DOWNLOAD;
        }

        int currentYear = LocalDate.now().getYear();
        return IntStream.rangeClosed(currentYear - countOfPreviousYears, currentYear).boxed().collect(Collectors.toList());
    }

    public static String getFileNameFromHost(String host) {
        if (host == null || host.isEmpty()) {
            LOGGER.warn("Cannot read file name from null host");
            return DataImporterConst.DEFAULT_FILE_NAME;
        }

        String[] split = host.split(DataImporterConst.SEASON_MONTH);
        return split[1] + DataImporterConst.FILE_TYPE;
    }
}
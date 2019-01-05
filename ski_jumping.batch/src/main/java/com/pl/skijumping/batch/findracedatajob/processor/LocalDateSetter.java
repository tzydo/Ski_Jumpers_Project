//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;
//
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.Months;
//
//import java.time.DateTimeException;
//import java.time.LocalDate;
//import java.util.List;
//
//class LocalDateSetter {
//    private final List<String> dateList;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    public LocalDateSetter(List<String> dateList, DiagnosticMonitor diagnosticMonitor) {
//        this.dateList = dateList;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    public LocalDate setDate() throws InternalServiceException {
//        if (dateList == null || dateList.isEmpty()) {
//            diagnosticMonitor.logError("Not found any value to create date", getClass());
//            return null;
//        }
//
//        if (dateList.size() > 3) {
//            throw new InternalServiceException(
//                    String.format("Cannot set date from list:%s values exceed the range", dateList));
//        }
//
//        Integer day = getValue(dateList, 0);
////        Integer month = Months.getValue(dateList.get(1));
//        Integer month = null;
//        Integer year = getValue(dateList, 2);
//
//        try {
//            return LocalDate.of(year, month, day);
//        } catch (DateTimeException | NullPointerException e) {
//            diagnosticMonitor.logError(
//                    String.format("Cannot set date from values: day = %d, month = %d, year = %d", day, month, year)
//                    , getClass());
//            return null;
//        }
//    }
//
//    private Integer getValue(List<String> dateList, int needValue) {
//        if (dateList.size() < needValue) {
//            diagnosticMonitor.logError(
//                    String.format("Cannot get %d from list, value exceed the range", needValue), getClass());
//            return null;
//        }
//
//        Integer value = null;
//        try {
//            value = Integer.parseInt(dateList.get(needValue));
//        } catch (NumberFormatException e) {
//            diagnosticMonitor.logError(
//                    String.format("Cannot change value from string:%s to integer!", dateList.get(needValue)), getClass());
//        }
//        return value;
//    }
//}

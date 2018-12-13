//package com.pl.skijumping.batch.jumpresultsynchronize.writer.creator;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//class ValueMapper {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ValueMapper.class);
//
//    private ValueMapper() {
//        //
//    }
//
//    static Integer getIntegerValue(List<String> resultDataSecondStep, int index) {
//        if (resultDataSecondStep.isEmpty() || resultDataSecondStep.size() < index) {
//            LOGGER.warn("Cannot get value from list where size is less than the index");
//            return null;
//        }
//        try {
//            return Integer.parseInt(resultDataSecondStep.get(index));
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//
//    static Double getDoubleValue(List<String> resultDataSecondStep, int index) {
//        if (resultDataSecondStep.isEmpty() || resultDataSecondStep.size() < index) {
//            LOGGER.warn("Cannot get value from list where size is less than the index");
//            return null;
//        }
//        try {
//            return Double.valueOf(resultDataSecondStep.get(index));
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
//}

//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;
//
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LocalDateSetterTest {
//    @Mock
//    DiagnosticMonitor diagnosticMonitor;
//
//    @Test
//    public void setDateTest() throws InternalServiceException {
//        List<String> dateList = Arrays.asList("25", "MAR", "2018");
//        LocalDateSetter localDateSetter = new LocalDateSetter(dateList, diagnosticMonitor);
//        LocalDate localDate = localDateSetter.setDate();
//        Assertions.assertThat(localDate).isNotNull();
//        Assertions.assertThat(localDate).isEqualTo(LocalDate.of(2018, 3, 25));
//    }
//
//    @Test
//    public void setDateWhenMoreValuesTest() {
//        List<String> dateList = Arrays.asList("test", "test1", "test2", "test3");
//        LocalDateSetter localDateSetter = new LocalDateSetter(dateList, diagnosticMonitor);
//        Throwable throwable = Assertions.catchThrowable(localDateSetter::setDate);
//
//        Assertions.assertThat(throwable).isInstanceOf(InternalServiceException.class);
//    }
//
//    @Test
//    public void setDateWhenCannotParseValuesTest() throws InternalServiceException {
//        List<String> dateList = Arrays.asList("test", "test1", "test2");
//        LocalDateSetter localDateSetter = new LocalDateSetter(dateList, diagnosticMonitor);
//        LocalDate localDate = localDateSetter.setDate();
//        Assertions.assertThat(localDate).isNull();
//    }
//}
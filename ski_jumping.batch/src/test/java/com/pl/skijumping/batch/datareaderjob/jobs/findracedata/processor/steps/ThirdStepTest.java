//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;
//
//import com.pl.skijumping.common.exception.InternalServiceException;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ThirdStepTest {
//
//    @Mock
//    DiagnosticMonitor diagnosticMonitor;
//
//    private final String testWords =
//            "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"   data-race-sectorcode=\"JP\"><div class=\"date-flag\"><div><divclass=\"sprite-big-flag big-flag-SLO\"></div>SLO</div></div><div class=\"date-text\"><h6>Planica</h6><div>";
//
//    @Test
//    public void setValuesTest() throws InternalServiceException {
//        ThirdStep thirdStep = new ThirdStep(testWords, diagnosticMonitor);
//        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());
//
//        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO();
//        expectedDataRaceDTO.setRaceId(4961l);
//        expectedDataRaceDTO.setShortCountryName("SLO");
//        expectedDataRaceDTO.setCity("Planica");
//
//        Assertions.assertThat(actualDataRaceDTO).isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
//    }
//
//    @Test
//    public void setValuesWhenNullTest() throws InternalServiceException {
//        ThirdStep thirdStep = new ThirdStep(null, diagnosticMonitor);
//        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());
//
//        Assertions.assertThat(actualDataRaceDTO).isNull();
//    }
//
//    @Test
//    public void setValuesWhenEmptyTest() throws InternalServiceException {
//        ThirdStep thirdStep = new ThirdStep("", diagnosticMonitor);
//        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());
//
//        Assertions.assertThat(actualDataRaceDTO).isNull();
//    }
//
//    @Test
//    public void setValuesWhenWrongDataRaceParamTest() throws InternalServiceException {
//        ThirdStep thirdStep = new ThirdStep("data-race=\"one\"", diagnosticMonitor);
//
//        Assertions.assertThat(thirdStep.setValues(new DataRaceDTO())).isNull();
//    }
//
//    @Test
//    public void setValuesWhenNotFoundMatchingValuesTest() throws InternalServiceException {
//        String words = "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"";
//        ThirdStep thirdStep = new ThirdStep(words, diagnosticMonitor);
//        Assertions.assertThat(thirdStep.setValues(new DataRaceDTO())).isNull();
//    }
//}
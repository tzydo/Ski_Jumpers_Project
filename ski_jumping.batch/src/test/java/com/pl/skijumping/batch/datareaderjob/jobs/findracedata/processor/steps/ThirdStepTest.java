package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ThirdStepTest {

    private final String testWords =
            "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"   data-race-sectorcode=\"JP\"><div class=\"date-flag\"><div><divclass=\"sprite-big-flag big-flag-SLO\"></div>SLO</div></div><div class=\"date-text\"><h6>Planica</h6><div>";

    @Test
    public void setValuesTest() throws InternalServiceException {
        ThirdStep thirdStep = new ThirdStep(testWords);
        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());

        DataRaceDTO expectedDataRaceDTO = new DataRaceDTO();
        expectedDataRaceDTO.setRaceId(4961);
        expectedDataRaceDTO.setShortCountryName("SLO");
        expectedDataRaceDTO.setCity("Planica");

        Assertions.assertThat(actualDataRaceDTO).isEqualToComparingFieldByFieldRecursively(expectedDataRaceDTO);
    }

    @Test
    public void setValuesWhenNullTest() throws InternalServiceException {
        ThirdStep thirdStep = new ThirdStep(null);
        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());

        Assertions.assertThat(actualDataRaceDTO).isNull();
    }

    @Test
    public void setValuesWhenEmptyTest() throws InternalServiceException {
        ThirdStep thirdStep = new ThirdStep("");
        DataRaceDTO actualDataRaceDTO = thirdStep.setValues(new DataRaceDTO());

        Assertions.assertThat(actualDataRaceDTO).isNull();
    }

    @Test
    public void setValuesWhenWrongDataRaceParamTest() throws InternalServiceException {
        ThirdStep thirdStep = new ThirdStep("data-race=\"one\"");

        Assertions.assertThat(thirdStep.setValues(new DataRaceDTO())).isNull();
    }

    @Test
    public void setValuesWhenNotFoundMatchingValuesTest() throws InternalServiceException {
        String words = "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"";
        ThirdStep thirdStep = new ThirdStep(words);
        Assertions.assertThat(thirdStep.setValues(new DataRaceDTO())).isNull();
    }
}
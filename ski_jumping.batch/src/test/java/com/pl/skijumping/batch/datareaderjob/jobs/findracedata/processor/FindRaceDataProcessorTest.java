package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor;

import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FindRaceDataProcessorTest {

    private String words =
            "<div class=\"dates\"> <div class=\"date\"> <div class=\"date-label\"> " +
            "<span class=\"date-day\">25</span> <span class=\"date-month\">MAR</span> " +
            "<span class=\"date-year\">2018</span> </div> <div class=\"date-content\"> " +
            "<div class=\"date-race men active\" data-race=\"4961\" data-race-sectorcode=\"JP\"> " +
            "<div class=\"date-flag\"> <div> <div class=\"sprite-big-flag big-flag-SLO\"></div> " +
            "SLO </div> </div> <div class=\"date-text\"> <h6>Planica</h6> <div> " +
            "<p>World Cup</p> <p>Ski Jumping</p> <p>Men's HS240</p> </div> </div> " +
            "</div> <div id=\"mobile_race_4961\" class=\"container_mobile_race\"></div> " +
            "<div class=\"date-race ladies \" data-race=\"5015\" data-race-sectorcode=\"JP\"> " +
            "<div class=\"date-flag\"> <div> <div  class=\"sprite-big-flag big-flag-GER\"></div> " +
            "GER </div>  </div>  <div class=\"date-text\">  <h6>Oberstdorf</h6>  <div>  <p>World Cup</p> " +
            "<p>Ski Jumping</p>  <p>Ladies' HS106</p>  </div>  </div>  </div>  <div id=\"mobile_race_5015\" " +
            "class=\"container_mobile_race\"></div>  </div> <!-- date-content -->";

    @Test
    public void findDataTest() throws InternalServiceException {
        List<DataRaceDTO> expectedDataRaceDTOList = Arrays.asList(
            new DataRaceDTO().builder()
                    .date(LocalDate.of(2018,3,25)).raceId(4961).city("Planica")
                    .shortCountryName("SLO").competitionName("World Cup").competitionType("Men's HS240").build(),
            new DataRaceDTO().builder()
                    .date(LocalDate.of(2018,3,25)).raceId(5015).city("Oberstdorf")
                    .shortCountryName("GER").competitionName("World Cup").competitionType("Ladies' HS106").build()
        );
        FindRaceDataProcessor findRaceDataProcessor = new FindRaceDataProcessor(words);
        List<DataRaceDTO> actualMatchingWordsList = findRaceDataProcessor.findData();
        Assertions.assertThat(actualMatchingWordsList).isNotEmpty();
        Assertions.assertThat(actualMatchingWordsList).hasSize(2);
        Assertions.assertThat(actualMatchingWordsList).isEqualTo(expectedDataRaceDTOList);
    }
}
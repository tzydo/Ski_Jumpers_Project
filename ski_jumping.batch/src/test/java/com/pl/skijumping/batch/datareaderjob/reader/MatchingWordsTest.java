package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MatchingWordsTest {
    private DiagnosticMonitor diagnosticMonitor;

    @Before
    public void setup() {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
    }

    @Test
    public void getSeasonDataMatchWordsTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);

        String text = "<div id=\"filters\">" +
                "<fieldset>" +
                "<h4>Filter by season</h4>" +
                "<div class=\"row\">" +
                "<div class=\"large-2 small-12 columns\">" +
                "<select name=\"season\" id=\"season\" class=\"queryChange\">" +
                "<option value=\"2019\" >2019</option>" +
                "<option value=\"2018\" selected=\"selected\">2018</option>" +
                "<option value=\"2017\" >2017</option>" +
                "<option value=\"2016\" >2016</option>" +
                "<option value=\"2015\" >2015</option>";

        Optional<List<String>> seasonDataMatchWords = matchingWords.getTournamentYears(text);
        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
        Assertions.assertThat(seasonDataMatchWords.get()).isNotEmpty();
        Assertions.assertThat(seasonDataMatchWords.get().size()).isEqualTo(5);
        Assertions.assertThat(seasonDataMatchWords.get()).containsAll(Arrays.asList("2015", "2016", "2017", "2018", "2019"));
    }

    @Test
    public void getRaceDataFirstStepTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);

        String text = "<div class=\"date\">testText<!-- date-content -->" +
                "<div class=\"date\">secondTestText<!-- date-content -->" +
                "<div class=\"date\">thirdTestText<!-- date-content -->";

        Optional<List<String>> seasonDataMatchWords = matchingWords.getRaceDataFirstStep(text);
        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
        Assertions.assertThat(seasonDataMatchWords.get()).isNotEmpty();
        Assertions.assertThat(seasonDataMatchWords.get().size()).isEqualTo(3);
        Assertions.assertThat(seasonDataMatchWords.get()).
                containsAll(Arrays.asList("testText", "secondTestText", "thirdTestText"));
    }

    @Test
    public void getRaceDataSecondStepTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);

        String text = "data-race=\"testText<div id=\"mobile_race" +
                "data-race=\"secondTestText<div id=\"mobile_race" +
                "data-race=\"thirdTestText<div id=\"mobile_race";

        Optional<List<String>> seasonDataMatchWords = matchingWords.getRaceDataSecondStep(text);
        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
        Assertions.assertThat(seasonDataMatchWords.get()).isNotEmpty();
        Assertions.assertThat(seasonDataMatchWords.get().size()).isEqualTo(3);
        Assertions.assertThat(seasonDataMatchWords.get()).
                containsAll(Arrays.asList("data-race=\"testText<div id=\"mobile_race",
                        "data-race=\"secondTestText<div id=\"mobile_race",
                        "data-race=\"thirdTestText<div id=\"mobile_race"));
    }

    @Test
    public void getRaceDataThirdStepTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String words = "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"   data-race-sectorcode=\"JP\"><div class=\"date-flag\"><div><divclass=\"sprite-big-flag big-flag-SLO\"></div>SLO</div></div><div class=\"date-text\"><h6>Planica</h6><div>";

        Optional<List<String>> raceDataThirdStep = matchingWords.getRaceDataThirdStep(words);
        Assertions.assertThat(raceDataThirdStep.isPresent()).isTrue();
        Assertions.assertThat(raceDataThirdStep.get()).hasSize(3);
        Assertions.assertThat(raceDataThirdStep.get()).containsAll(Arrays.asList("4961", "SLO", "Planica"));
    }

    @Test
    public void getRaceDataThirdStepWhenNullTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDataThirdStep = matchingWords.getRaceDataThirdStep(null);
        Assertions.assertThat(raceDataThirdStep.isPresent()).isFalse();
    }

    @Test
    public void getRaceDataFourthStepTest() {
        String testWords =
                "<h6>Planica</h6><div><p>World Cup</p><p>Ski Jumping</p><p>Men's HS240</p></div></div>";
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDataFourthStep = matchingWords.getRaceDataFourthStep(testWords);
        Assertions.assertThat(raceDataFourthStep.isPresent()).isTrue();
        Assertions.assertThat(raceDataFourthStep.get()).isNotEmpty();
        Assertions.assertThat(raceDataFourthStep.get()).hasSize(3);
        Assertions.assertThat(raceDataFourthStep.get()).containsAll(Arrays.asList("World Cup", "Ski Jumping", "Men's HS240"));
    }

    @Test
    public void getRaceDataFourthStepWhenNullTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDataFourthStep = matchingWords.getRaceDataFourthStep(null);
        Assertions.assertThat(raceDataFourthStep.isPresent()).isFalse();
    }

    @Test
    public void getRaceDateTest() {
        String words = " <div class=\"date\">" +
                "<div class=\"date-label\">" +
                "<span class=\"date-day\">25</span>" +
                "<span class=\"date-month\">MAR</span>" +
                "<span class=\"date-year\">2018</span>";

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDate = matchingWords.getRaceDate(words);
        Assertions.assertThat(raceDate.isPresent()).isTrue();
        Assertions.assertThat(raceDate.get()).hasSize(3);
        Assertions.assertThat(raceDate.get()).containsAll(Arrays.asList("25", "MAR", "2018"));
    }
}
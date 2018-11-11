package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
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

        String text = "<select id=\"seasoncode\" tabindex=\"3\" data-module=\"select\" title=\"select a season\" onchange=\"fct_change_calendar_season(this);\" name=\"seasoncode\">\n" +
                "\t<option value=\"2019\" selected=\"selected\">2019</option>\n" +
                "\t<option value=\"2018\">2018</option>\n" +
                "\t<option value=\"2017\">2017</option>\n" +
                "\t<option value=\"2016\">2016</option>\n" +
                "\t<option value=\"2015\">2015</option></select>";


        Optional<List<String>> seasonDataMatchWords = matchingWords.getTournamentYears(text);
        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
        Assertions.assertThat(seasonDataMatchWords.get()).isNotEmpty();
        Assertions.assertThat(seasonDataMatchWords.get().size()).isEqualTo(5);
        Assertions.assertThat(seasonDataMatchWords.get()).containsAll(Arrays.asList("<option value=\"2019\"", "<option value=\"2018\"", "<option value=\"2017\"", "<option value=\"2016\"", "<option value=\"2015\""));
    }

    @Test
    public void getSeasonDataMatchWordsSecondFilterTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "<option value=\"2016\"";

        Optional<List<String>> seasonDataMatchWords = matchingWords.getTournamentYearsFilterData(text);
        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
        Assertions.assertThat(seasonDataMatchWords.get()).isEqualTo(Collections.singletonList("2016"));
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
                containsAll(Arrays.asList(
                        "<div class=\"date\">testText<!-- date-content -->",
                        "<div class=\"date\">secondTestText<!-- date-content -->",
                        "<div class=\"date\">thirdTestText<!-- date-content -->"));
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

    @Test
    public void getResultDataFilterTest() {
        String words = "<tbody><thead></thead><tr><th data-hide='phone'>Points</th></tr></tbody>values";
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDate = matchingWords.getJumpResultDataFilter(words);
        Assertions.assertThat(raceDate.isPresent()).isTrue();
        Assertions.assertThat(raceDate.get()).isNotEmpty();
        Assertions.assertThat(raceDate.get()).isEqualTo(Arrays.asList("<tr><th data-hide='phone'>Points</th></tr>"));
    }

    @Test
    public void getResultDataFirstStepTest() {
        String words =
                "</thead><tr><td class='i0' align='right'></tr>" +
                        "<tr><td class='i1' align='right'></tr>"+
                        "<tr><td class='i0' align='right'></tr></thead>";

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDate = matchingWords.getJumpResultDataFirstStep(words);
        Assertions.assertThat(raceDate.isPresent()).isTrue();
        Assertions.assertThat(raceDate.get()).isNotEmpty();
        Assertions.assertThat(raceDate.get()).hasSize(3);
        Assertions.assertThat(raceDate.get()).isEqualTo(Arrays.asList(
                "<td class='i0' align='right'>",
                "<td class='i1' align='right'>",
                "<td class='i0' align='right'>"));
    }

    @Test
    public void getResultDataSecondStepTest() {
        String words =
                "<td class='i0' align='right'>1</td>" +
                 "<td class='i1' align='right'>2</td>";

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> raceDate = matchingWords.getJumpResultDataSecondStep(words);
        Assertions.assertThat(raceDate.isPresent()).isTrue();
        Assertions.assertThat(raceDate.get()).isNotEmpty();
        Assertions.assertThat(raceDate.get()).hasSize(2);
        Assertions.assertThat(raceDate.get()).isEqualTo(Arrays.asList("1","2"));
    }

    @Test
    public void getSkiJumperNameTest() {
        String words = "test test <a test test>value value</a>test test";
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String raceDate = matchingWords.getSkiJumperName(words);
        Assertions.assertThat(raceDate).isNotEmpty();
        Assertions.assertThat(raceDate).isEqualTo("value value");
    }
}
package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchingWordsTest {
    private DiagnosticMonitor diagnosticMonitor;

    @Before
    public void setup() {
        diagnosticMonitor = SetupUtilTests.getDiagnosticMonitorMock();
    }

    @Test
    public void getEventIdTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39207&seasonCode=2017\n"+
                "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39224&seasonCode=2017"+
                "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39038&seasonCode=2017"+
                "https://www.fis-ski.com/DB/general/event-details.html?sectorcode=JP&eventid=39225&seasonCode=2017";

        Set<String> eventIdMatchWords = matchingWords.getEventIds(text);
        Assertions.assertThat(eventIdMatchWords.isEmpty()).isFalse();
        Assertions.assertThat(eventIdMatchWords.size()).isEqualTo(4);
        Assertions.assertThat(eventIdMatchWords).containsAll(Arrays.asList("39207", "39224", "39038", "39225"));
    }

    @Test
    public void getRaceDataTemplateTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text ="<div class=\"table-row reset-padding\">test<span class=\"btn__label\">download" +
                "<div class=\"table-row reset-padding\">test2<span class=\"btn__label\">download" +
                "<div class=\"table-row reset-padding\">test3<span class=\"btn__label\">download";

        Set<String> templateMatchingWords = matchingWords.getRaceDataTemplate(text);
        Assertions.assertThat(templateMatchingWords.isEmpty()).isFalse();
        Assertions.assertThat(templateMatchingWords.size()).isEqualTo(3);
        Assertions.assertThat(templateMatchingWords).containsAll(Arrays.asList("test<span class=", "test2<span class=", "test3<span class="));
    }

    @Test
    public void getRaceDataIdRaceTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text ="px-md-1 px-lg-1 pl-xs-1 g-lg-2 g-md-3 g-sm-2 g-xs-4 justify-left\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5179\" target= ";

        String templateMatchingWords = matchingWords.getRaceDataIdRace(text);
        Assertions.assertThat(templateMatchingWords.isEmpty()).isFalse();
        Assertions.assertThat(templateMatchingWords).isEqualTo("5179");
    }

    @Test
    public void getRaceDataIdWhenNotContainTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text ="px-md-1 px-lg-1 pl-xs-1 g-lg-2 g-md-3 g-sm-2 g-xs-4 justify-left\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5179\"";

        String templateMatchingWords = matchingWords.getRaceDataIdRace(text);
        Assertions.assertThat(templateMatchingWords).isNull();
    }

    @Test
    public void getRaceDataIsCancelledWhenNotTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text ="<span class=\"status__item status__item_selected\" title=\"PDF available\">P</span></div><div class=\"status__item-wrapper\"><span class=\"status__item\" title=\"No changes\">C</span>\n" +
                "<span class=\"status__item\" title=\"Not cancelled\">C</span></div></div></div></a>";

        Boolean isCancelled = matchingWords.checkRaceDataIsCancelled(text);
        Assertions.assertThat(isCancelled).isFalse();
    }

    @Test
    public void getRaceDataIsCancelledWhenTrueTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text ="<span class=\"status__item status__item_selected\" title=\"PDF available\">P</span></div><div class=\"status__item-wrapper\"><span class=\"status__item\" title=\"No changes\">C</span>\n" +
                "<span class=\"status__item\" title=\"Cancelled\">C</span></div></div></div></a>";

        Boolean isCancelled = matchingWords.checkRaceDataIsCancelled(text);
        Assertions.assertThat(isCancelled).isTrue();
    }

    @Test
    public void getRaceDataDateTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text =" <a class=\"px-md-1 px-lg-1 pl-xs-1 g-lg-2 g-md-3 g-sm-2 g-xs-4 justify-left\" href=\"https://www.fis-ski.com/DB/general/results.html?sectorcode=JP&raceid=5179\" target=\"_self\">27 Dec</a>";
        String date = matchingWords.getRaceDataDate(text);
        Assertions.assertThat(date).isNotNull();
        Assertions.assertThat(date).isEqualTo("27 Dec");
    }

    @Test
    public void getRaceDataJumpCategoryTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "testtest<div class=\"g-xs-12 justify-left\">COC</div><div class=\"g-xs-12 justify-right bold\">test test";
        String jumpCategory = matchingWords.getRaceDataJumpCategoryShortName(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("COC");
    }

    @Test
    public void getRaceDataCodexTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "t-padding\"><div class=\"link__text\" style=\"text-align:center\">3177</div>";
        String jumpCategory = matchingWords.getRaceDataCodex(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("3177");
    }

    @Test
    public void getRaceDataGenderTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = " <div class=\"gender\"><div class=\"gender__inner\"><div class=\"gender__item gender__item_m\">M</div></div></div></a>";
        String jumpCategory = matchingWords.getRaceDataGender(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("M");
    }

    @Test
    public void getRaceDataCompetitionType() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "class=\"split-row__item split-row__item_text_medium\"><div class=\"g-xs-24 justify-left\"><div class=\"clip\">HS140</div></div></div></div>";
        String jumpCategory = matchingWords.getRaceDataCompetitionType(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("HS140");
    }








//
//    @Test
//    public void getRaceDataFirstStepTest() {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//
//        String text = "<div class=\"date\">testText<!-- date-content -->" +
//                "<div class=\"date\">secondTestText<!-- date-content -->" +
//                "<div class=\"date\">thirdTestText<!-- date-content -->";
//
//        Set<String> seasonDataMatchWords = matchingWords.getRaceDataFirstStep(text);
//        Assertions.assertThat(seasonDataMatchWords.isEmpty()).isFalse();
//        Assertions.assertThat(seasonDataMatchWords.size()).isEqualTo(3);
//        Assertions.assertThat(seasonDataMatchWords).
//                containsAll(Arrays.asList(
//                        "<div class=\"date\">testText<!-- date-content -->",
//                        "<div class=\"date\">secondTestText<!-- date-content -->",
//                        "<div class=\"date\">thirdTestText<!-- date-content -->"));
//    }
//
//    @Test
//    public void getRaceDataSecondStepTest() {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//
//        String text = "data-race=\"testText<div id=\"mobile_race" +
//                "data-race=\"secondTestText<div id=\"mobile_race" +
//                "data-race=\"thirdTestText<div id=\"mobile_race";
//
//        Set<String> seasonDataMatchWords = matchingWords.getRaceDataSecondStep(text);
//        Assertions.assertThat(seasonDataMatchWords.isEmpty()).isFalse();
//        Assertions.assertThat(seasonDataMatchWords.size()).isEqualTo(3);
//        Assertions.assertThat(seasonDataMatchWords)
//                .containsAll(Arrays.asList("data-race=\"testText<div id=\"mobile_race",
//                        "data-race=\"secondTestText<div id=\"mobile_race",
//                        "data-race=\"thirdTestText<div id=\"mobile_race"));
//    }
//
//    @Test
//    public void getRaceDataThirdStepTest() {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        String words = "<div class=\"date-content\"><div class=\"date-race men active\"data-race=\"4961\"   data-race-sectorcode=\"JP\"><div class=\"date-flag\"><div><divclass=\"sprite-big-flag big-flag-SLO\"></div>SLO</div></div><div class=\"date-text\"><h6>Planica</h6><div>";
//
//        Set<String> raceDataThirdStep = matchingWords.getRaceDataThirdStep(words);
//        Assertions.assertThat(raceDataThirdStep.isEmpty()).isFalse();
//        Assertions.assertThat(raceDataThirdStep).hasSize(3);
//        Assertions.assertThat(raceDataThirdStep).containsAll(Arrays.asList("4961", "SLO", "Planica"));
//    }
//
//    @Test
//    public void getRaceDataThirdStepWhenNullTest() {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDataThirdStep = matchingWords.getRaceDataThirdStep(null);
//        Assertions.assertThat(raceDataThirdStep.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void getRaceDataFourthStepTest() {
//        String testWords =
//                "<h6>Planica</h6><div><p>World Cup</p><p>Ski Jumping</p><p>Men's HS240</p></div></div>";
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDataFourthStep = matchingWords.getRaceDataFourthStep(testWords);
//        Assertions.assertThat(raceDataFourthStep.isEmpty()).isFalse();
//        Assertions.assertThat(raceDataFourthStep).hasSize(3);
//        Assertions.assertThat(raceDataFourthStep).containsAll(Arrays.asList("World Cup", "Ski Jumping", "Men's HS240"));
//    }
//
//    @Test
//    public void getRaceDataFourthStepWhenNullTest() {
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDataFourthStep = matchingWords.getRaceDataFourthStep(null);
//        Assertions.assertThat(raceDataFourthStep.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void getRaceDateTest() {
//        String words = " <div class=\"date\">" +
//                "<div class=\"date-label\">" +
//                "<span class=\"date-day\">25</span>" +
//                "<span class=\"date-month\">MAR</span>" +
//                "<span class=\"date-year\">2018</span>";
//
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDate = matchingWords.getRaceDate(words);
//        Assertions.assertThat(raceDate.isEmpty()).isFalse();
//        Assertions.assertThat(raceDate).hasSize(3);
//        Assertions.assertThat(raceDate).containsAll(Arrays.asList("25", "MAR", "2018"));
//    }
//
//    @Test
//    public void getResultDataFilterTest() {
//        String words = "<tbody><thead></thead><tr><th data-hide='phone'>Points</th></tr></tbody>values";
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDate = matchingWords.getJumpResultDataFilter(words);
//        Assertions.assertThat(raceDate.isEmpty()).isFalse();
//        Assertions.assertThat(raceDate).isNotEmpty();
//        Assertions.assertThat(raceDate).isEqualTo(Arrays.asList("<tr><th data-hide='phone'>Points</th></tr>"));
//    }
//
//    @Test
//    public void getResultDataFirstStepTest() {
//        String words =
//                "</thead><tr><td class='i0' align='right'></tr>" +
//                        "<tr><td class='i1' align='right'></tr>"+
//                        "<tr><td class='i0' align='right'></tr></thead>";
//
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDate = matchingWords.getJumpResultDataFirstStep(words);
//        Assertions.assertThat(raceDate.isEmpty()).isFalse();
//        Assertions.assertThat(raceDate).hasSize(3);
//        Assertions.assertThat(raceDate).isEqualTo(Arrays.asList(
//                "<td class='i0' align='right'>",
//                "<td class='i1' align='right'>",
//                "<td class='i0' align='right'>"));
//    }
//
//    @Test
//    public void getResultDataSecondStepTest() {
//        String words =
//                "<td class='i0' align='right'>1</td>" +
//                 "<td class='i1' align='right'>2</td>";
//
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        Set<String> raceDate = matchingWords.getJumpResultDataSecondStep(words);
//        Assertions.assertThat(raceDate.isEmpty()).isFalse();
//        Assertions.assertThat(raceDate).hasSize(2);
//        Assertions.assertThat(raceDate).isEqualTo(Arrays.asList("1","2"));
//    }
//
//    @Test
//    public void getSkiJumperNameTest() {
//        String words = "test test <a test test>value value</a>test test";
//        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
//        String raceDate = matchingWords.getSkiJumperName(words);
//        Assertions.assertThat(raceDate).isNotEmpty();
//        Assertions.assertThat(raceDate).isEqualTo("value value");
//    }
}
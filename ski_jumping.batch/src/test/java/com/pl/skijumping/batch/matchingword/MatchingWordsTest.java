package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
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
    public void getRaceDataTemplateFullTest() throws IOException {
        String filePath = Paths.get(new ClassPathResource("event2.txt").getURI()).toString();

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String fileContent = dataReader.read(filePath);
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Set<String>templateSet = matchingWords.getRaceDataTemplate(fileContent);
        Assertions.assertThat(templateSet).hasSize(3);

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
        String text ="<div class=\"split-row__item split-row__item_text_medium reset-padding\"><div class=\"g-xs-24 justify-left\">15 Feb</div></div></div></a><div class=\"g-lg-2";
        String date = matchingWords.getRaceDataDate(text);
        Assertions.assertThat(date).isNotNull();
        Assertions.assertThat(date).isEqualTo("15 Feb");
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
    public void getRaceDataGenderTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = " <div class=\"gender\"><div class=\"gender__inner\"><div class=\"gender__item gender__item_m\">M</div></div></div></a>";
        String jumpCategory = matchingWords.getRaceDataGender(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("M");
    }

    @Test
    public void getRaceDataCityTest() {
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String text = "<h1class=\"headingheading_l2heading_off-sm-styleheading_plainevent-header__name\">Oberstdorf(GER)</h1></div></div></div></div><sectionclass=\"section\"><divclass=\"section__inner\"><divclass=\"section__content\"><headerclass=\"section__header\"><h3class=\"headingheading_l3\">SkiJumpingCompetitions</h3></header>";
        String jumpCategory = matchingWords.getRaceDataCity(text);
        Assertions.assertThat(jumpCategory).isNotNull();
        Assertions.assertThat(jumpCategory).isNotEmpty();
        Assertions.assertThat(jumpCategory).isEqualTo("Oberstdorf(GER)");
    }
}
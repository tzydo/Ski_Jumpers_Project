package com.pl.skijumping.batch.datareaderjob.reader;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Profile("test")
public class MatchingWordsTest {

    @Test
    public void getSeasonDataMatchWordsTest() {
        MatchingWords matchingWords = new MatchingWords();

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
        MatchingWords matchingWords = new MatchingWords();

        String text = "<div class=\"date\">testText<!-- date-content -->" +
                "<div class=\"date\">secondTestText<!-- date-content -->"+
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
        MatchingWords matchingWords = new MatchingWords();

        String text = "data-race=\"testText<div id=\"mobile_race" +
                "data-race=\"secondTestText<div id=\"mobile_race"+
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
}
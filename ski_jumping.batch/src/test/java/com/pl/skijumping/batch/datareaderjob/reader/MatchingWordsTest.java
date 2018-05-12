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

//    @Test
//    public void getDataRaceMatchingWordsTest() {
//        MatchingWords matchingWords = new MatchingWords();
//        String text ="<div class=\"date-content\">" +
//                "<div class=\"date-race ladies \"\n" +
//                "data-race=\"5014\" data-race-sectorcode=\"JP\">\n" +
//                "<div class=\"date-flag\"><div><div\n" +
//                "class=\"sprite-big-flag big-flag-GER\">" +
//                "</div>GER</div>" +
//                "</div>\n" +
//                "<div class=\"date-text\">\n" +
//                "<h6>Oberstdorf</h6>\n" +
//                "<div>\n" +
//                "<p>World Cup</p>\n" +
//                "<p>Ski Jumping</p>\n" +
//                "<p>Ladies' HS106</p>\n" +
//                "</div></div></div>\n" +
//                "<div id=\"mobile_race_5014\"\n" +
//                "class=\"container_mobile_race\"></div>\n" +
//                "<div class=\"date-race men \"\n" +
//                "data-race=\"4960\" data-race-sectorcode=\"JP\">\n" +
//                "<div class=\"date-flag\">\n" +
//                "<div><div\n" +
//                "class=\"sprite-big-flag big-flag-SLO\"></div>\n" +
//                "SLO</div>\n" +
//                "</div>\n" +
//                "<div class=\"date-text\">\n" +
//                "<h6>Planica</h6>\n" +
//                "<div>\n" +
//                "<p>World Cup</p>\n" +
//                "<p>Ski Jumping</p>\n" +
//                "<p>Men's Team HS240</p>\n" +
//                "</div></div></div>";
//
//        Optional<List<String>> seasonDataMatchWords = matchingWords.getRaceData(text);
//
//        Assertions.assertThat(seasonDataMatchWords.isPresent()).isTrue();
//        Assertions.assertThat(seasonDataMatchWords.get()).isNotEmpty();
//        Assertions.assertThat(seasonDataMatchWords.get().size()).isEqualTo(5);
//        Assertions.assertThat(seasonDataMatchWords.get()).containsAll(Arrays.asList("2015", "2016", "2017", "2018", "2019"));
//    }
}
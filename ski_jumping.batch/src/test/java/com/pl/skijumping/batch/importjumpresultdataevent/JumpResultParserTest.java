package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.dto.JumpResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class JumpResultParserTest {

    @Test
    public void testParseJumpResultData() {
        String text = "<a class=\"table-row\" href=\"https://www.fis-ski.com/DB/general/athlete-biography.html?sectorcode=jp&amp;competitorid=153102&amp;type=result\" " +
                "target=\"_self\"><div class=\"g-row container\"><div class=\"g-row justify-sb\"><div class=\"g-lg-1 g-md-1 g-sm-1 g-xs-2 justify-right pr-1 gray bold\">1" +
                "</div><div class=\"g-lg-1 g-md-1 g-sm-1 justify-right hidden-xs pr-1 gray\">60</div><div class=\"g-lg-2 g-md-2 g-sm-2 hidden-xs justify-right gray pr-1\">6198" +
                "</div><div class=\"g-lg-10 g-md-10 g-sm-9 g-xs-11 justify-left bold\">TAKANASHI Sara</div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 justify-left\">1996" +
                "</div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 justify-left\"><div class=\"country country_flag\"><span class=\"country__flag\"><span class=\"flag-JPN flag\"></span>" +
                "</span><span class=\"country__name-short\">JPN</span></div></div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">96.0</div><div class=\"g-lg-2 g-md-2 g-sm-2 " +
                "justify-right bold hidden-xs\">138.5</div><div class=\"g-lg-2 g-md-2 g-sm-3 g-xs-5 justify-right blue bold \">138.5</div><div class=\"g-lg-2 g-md-2 g-sm-3 g-xs-5 " +
                "justify-right bold hidden-sm hidden-xs\"><span class=\"hidden-md-up\">138.5</span></div></div></div></a>";

        JumpResultParser jumpResultParser = new JumpResultParser(null);
        JumpResultDTO actualJumpResultDTO = jumpResultParser.parseData(text);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(96.0)
                .pointsForFirstJump(138.5)
                .totalPoints(138.5)
                .competitorId("153102")
                .fisCodeId(6198)
                .jumperName("Sara Takanashi");

        Assertions.assertThat(actualJumpResultDTO).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }

    @Test
    public void testParseJumpResultDataWhenSecondJump() {
        String text = "<a class=\"table-row\" href=\"https://www.fis-ski.com/DB/general/athlete-biography.html?sectorcode=jp&amp;competitorid=155620&amp;type=result\" " +
                "target=\"_self\"><div class=\"g-row container\"><div class=\"g-row justify-sb\"><div class=\"g-lg-1 g-md-1 g-sm-1 g-xs-2 justify-right pr-1 gray bold\">1" +
                "</div><div class=\"g-lg-1 g-md-1 g-sm-1 justify-right hidden-xs pr-1 gray\">2</div><div class=\"g-lg-2 g-md-2 g-sm-2 hidden-xs justify-right gray pr-1\">6288" +
                "</div><div class=\"g-lg-6 g-md-6 g-sm-5 g-xs-11 justify-left bold\">KOBAYASHI Ryoyu </div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 " +
                "justify-left\">1996</div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 justify-left\"><div class=\"country country_flag\"><span class=\"country__flag\"><span class=\"flag-JPN " +
                "flag\"></span></span><span class=\"country__name-short\">JPN</span></div></div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">138.5</div><div " +
                "class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">147.2</div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">126.5</div><div " +
                "class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">135.1</div><div class=\"g-lg-2 g-md-2 g-sm-3 g-xs-5 justify-right blue bold \">282.3</div>" +
                "<div class=\"g-lg-2 g-md-2 g-sm-3 g-xs-5 justify-right bold hidden-sm hidden-xs\"><span class=\"hidden-md-up\">282.3</span></div></div></div></a>";

        JumpResultParser jumpResultParser = new JumpResultParser(null);
        JumpResultDTO actualJumpResultDTO = jumpResultParser.parseData(text);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(138.5)
                .pointsForFirstJump(147.2)
                .secondJump(126.5)
                .pointsForSecondJump(135.1)
                .totalPoints(282.3)
                .competitorId("155620")
                .fisCodeId(6288)
                .jumperName("Ryoyu Kobayashi");

        Assertions.assertThat(actualJumpResultDTO).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}
package com.pl.skijumping.batch.importjumpresultteamdataevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ResultParserTest {

    @Test
    public void testParseJumpResult() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitorMock);
        String template = "table-row_theme_additional\" href=\"https://www.fis-ski.com/DB/general/athlete-biography.html?sectorcode=jp&amp;competitorid=174083&amp;type=result\" target=\"_self\"><div class=\"g-row container\"><div class=\"g-row justify-sb\"><div class=\"g-lg-1 g-md-1 g-sm-1 g-xs-2 justify-right gray bold\"></div><div class=\"g-lg-2 g-md-2 g-sm-3 hidden-xs justify-right gray pr-1\">6681</div><div class=\"g-lg-9 g-md-9 g-sm-5 g-xs-11 justify-left bold\">MARUYAMA Nozomi</div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 justify-left\">1998</div><div class=\"g-lg-1 g-md-1 g-sm-2 g-xs-3 justify-left\"><div class=\"country country_flag country_medium\"><span class=\"country__name-short\"></span></div></div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">82.0</div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">112.7</div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">84.5</div><div class=\"g-lg-2 g-md-2 g-sm-2 justify-right bold hidden-xs\">111.2</div><div class=\"g-lg-2 g-md-2 g-sm-3 g-xs-5 justify-right\"></div>JUMP_RESULT_16";
        JumpResultDTO actualJumpResultDTO = ResultParser.parseJumpResult(matchingWords, template, 100.0);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(6681)
                .firstJump(82.0)
                .pointsForFirstJump(112.7)
                .secondJump(84.5)
                .pointsForSecondJump(111.2)
                .totalPoints(100.0)
                .fisCodeId(6681)
                .jumperName("Nozomi Maruyama")
                .competitorId("174083");

        Assertions.assertThat(actualJumpResultDTO).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}
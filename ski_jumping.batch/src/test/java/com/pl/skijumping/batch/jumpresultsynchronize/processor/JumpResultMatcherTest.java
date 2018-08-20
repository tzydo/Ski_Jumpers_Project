package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.SkiJumperService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class JumpResultMatcherTest {

    @Mock
    private SkiJumperService skiJumperService;

    private String words = "" +
            "<td class='i0' align='right'>1</td>" +
            "<td class='i0' align='right'>46</td>" +
            "<td class='i0' align='right'>6198</td>" +
            "<td class='i0'><a href=\\\"https://data.fis-ski.com/dynamic/athlete-biography." +
            "html?sector=JP&amp;competitorid=153102&amp;type=result\\\">TAKANASHI Sara</a></td>" +
            "<td class='i0' align='center'>1996</td>" +
            "<td class='i0' align='center'>JPN</td>" +
            "<td class='i0' align='right'>99.0</td>" +
            "<td class='i0' align='right'>126.3</td>" +
            "<td class='i0' align='right'>102.5</td>" +
            "<td class='i0' align='right'>124.1</td>" +
            "<td class='i0' align='right'>250.4</td>";

    @Test
    public void matchJumperData() {
        Mockito.when(skiJumperService.findOneByName(Mockito.anyString())).thenReturn(Optional.of(new SkiJumperDTO().id(1L)));
        Long raceDataId = 1L;

        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultMatcher jumpResultMatcher = new JumpResultMatcher(diagnosticMonitorMock, skiJumperService);
        JumpResultDTO jumpResultDTO = jumpResultMatcher.matchJumperData(words, raceDataId);


        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1).dataRaceId(raceDataId)
                .firstJump(99.0)
                .pointsForFirstJump(126.3)
                .secondJump(102.5)
                .pointsForSecondJump(124.1)
                .totalPoints(250.4)
                .jumperId(1L);

        Assertions.assertThat(jumpResultDTO).isEqualToIgnoringGivenFields(expectedJumpResultDTO, "id");
    }
}
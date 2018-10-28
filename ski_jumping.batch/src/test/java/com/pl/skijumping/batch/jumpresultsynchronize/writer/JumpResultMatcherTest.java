package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
import com.pl.skijumping.dto.JumpResultToSkiJumperDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import com.pl.skijumping.service.JumpResultToSkiJumperService;
import com.pl.skijumping.service.SkiJumperService;
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
    @Mock
    private JumpResultToDataRaceService jumpResultToDataRaceService;
    @Mock
    private JumpResultToSkiJumperService jumpResultToSkiJumperService;
    @Mock
    private JumpResultService jumpResultService;

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
    public void matchJumperDataTest() {
        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .firstJump(99.0)
                .pointsForFirstJump(126.3)
                .secondJump(102.5)
                .pointsForSecondJump(124.1)
                .totalPoints(250.4);

        Mockito.when(jumpResultService.save(Mockito.anyObject())).thenReturn(expectedJumpResultDTO.id(1L));
        Mockito.when(jumpResultService.findBy(Mockito.anyObject())).thenReturn(Optional.empty());
        Mockito.when(skiJumperService.findOneByName(Mockito.anyString())).thenReturn(Optional.of(new SkiJumperDTO().id(1L)));
        Long raceDataId = 1L;

        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultMatcher jumpResultMatcher = new JumpResultMatcher(
                diagnosticMonitorMock, skiJumperService, jumpResultToDataRaceService,
                jumpResultToSkiJumperService, jumpResultService);

        jumpResultMatcher.matchJumperData(words, raceDataId);

        Mockito.verify(jumpResultToSkiJumperService, Mockito.times(1))
                .save(new JumpResultToSkiJumperDTO().skiJumperId(expectedJumpResultDTO.getId()).jumpResultId(1L));
        Mockito.verify(jumpResultService, Mockito.times(1)).save(expectedJumpResultDTO.id(null));
        Mockito.verify(jumpResultToDataRaceService, Mockito.times(1))
                .save(new JumpResultToDataRaceDTO().dataRaceId(raceDataId).jumpResultId(1L));
    }
}
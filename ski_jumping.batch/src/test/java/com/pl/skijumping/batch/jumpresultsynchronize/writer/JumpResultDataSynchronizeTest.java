//package com.pl.skijumping.batch.jumpresultsynchronize.writer;
//
//import com.pl.skijumping.batch.SetupUtilTests;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.JumpResultDTO;
//import com.pl.skijumping.dto.JumpResultToDataRaceDTO;
//import com.pl.skijumping.dto.JumpResultToSkiJumperDTO;
//import com.pl.skijumping.dto.SkiJumperDTO;
//import com.pl.skijumping.service.JumpResultService;
//import com.pl.skijumping.service.JumpResultToDataRaceService;
//import com.pl.skijumping.service.JumpResultToSkiJumperService;
//import com.pl.skijumping.service.SkiJumperService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.Optional;
//
//@RunWith(MockitoJUnitRunner.class)
//public class JumpResultDataSynchronizeTest {
//    @Mock
//    private SkiJumperService skiJumperService;
//    @Mock
//    private JumpResultToDataRaceService jumpResultToDataRaceService;
//    @Mock
//    private JumpResultToSkiJumperService jumpResultToSkiJumperService;
//    @Mock
//    private JumpResultService jumpResultService;
//
//    private final String words =
//            "test</thead><tr>" +
//                    "<td class='i0' align='right'>&nbsp;1</td>" +
//                    "<td class='i0' align='right'>&nbsp;46</td>" +
//                    "<td class='i0' align='right'>&nbsp;6198</td>" +
//                    "<td class='i0'><a href=\"https://data.fis-ski.com/dynamic/athlete-biography." +
//                    "html?sector=JP&amp;competitorid=153102&amp;type=result\">TAKANASHI Sara</a>&nbsp;</td>" +
//                    "<td class='i0' align='center'>1996&nbsp;</td>" +
//                    "<td class='i0' align='center'>JPN&nbsp;</td>" +
//                    "<td class='i0' align='right'>&nbsp;99.0</td>" +
//                    "<td class='i0' align='right'>&nbsp;126.3</td>" +
//                    "<td class='i0' align='right'>&nbsp;102.5</td>" +
//                    "<td class='i0' align='right'>&nbsp;124.1</td>" +
//                    "<td class='i0' align='right'>&nbsp;250.4</td>" +
//                    "</tr></tbody>test";
//
//    @Test
//    public void transformDataTest() {
//        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
//                .rank(1)
//                .firstJump(99.0)
//                .pointsForFirstJump(126.3)
//                .secondJump(102.5)
//                .pointsForSecondJump(124.1)
//                .totalPoints(250.4);
//
//        Mockito.when(skiJumperService.findOneByName(Mockito.anyString())).thenReturn(Optional.of(new SkiJumperDTO().id(1L)));
//        Mockito.when(jumpResultService.findBy(Mockito.anyObject())).thenReturn(Optional.empty());
//        Mockito.when(jumpResultService.save(Mockito.anyObject())).thenReturn(expectedJumpResultDTO.id(1L));
//
//        Long raceDataId = 1L;
//        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
//        JumpResultDataSynchronize jumpResultDataSynchronize = new JumpResultDataSynchronize(
//                diagnosticMonitorMock, skiJumperService, raceDataId, jumpResultToDataRaceService, jumpResultToSkiJumperService, jumpResultService);
//        jumpResultDataSynchronize.transformData(words);
//
//        Mockito.verify(jumpResultToDataRaceService, Mockito.times(1))
//                .save(new JumpResultToDataRaceDTO().dataRaceId(raceDataId).jumpResultId(1L));
//
//        Mockito.verify(jumpResultToSkiJumperService, Mockito.times(1))
//                .save(new JumpResultToSkiJumperDTO().skiJumperId(expectedJumpResultDTO.getId()).jumpResultId(1L));
//    }
//}
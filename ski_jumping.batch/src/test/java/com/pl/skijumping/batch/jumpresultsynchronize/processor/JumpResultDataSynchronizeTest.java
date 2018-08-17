package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.SkiJumperDTO;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.SkiJumperService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class JumpResultDataSynchronizeTest {
    @Mock
    private SkiJumperService skiJumperService;

    private final String words =
            "test</thead><tr>" +
                    "<td class='i0' align='right'>&nbsp;1</td>"
                    + "<td class='i0' align='right'>&nbsp;46</td>" +
                    "<td class='i0' align='right'>&nbsp;6198</td>" +
                    "<td class='i0'><a href=\"https://data.fis-ski.com/dynamic/athlete-biography." +
                    "html?sector=JP&amp;competitorid=153102&amp;type=result\">TAKANASHI Sara</a>&nbsp;</td>" +
                    "<td class='i0' align='center'>1996&nbsp;</td>" +
                    "<td class='i0' align='center'>JPN&nbsp;</td>" +
                    "<td class='i0' align='right'>&nbsp;99.0</td>" +
                    "<td class='i0' align='right'>&nbsp;126.3</td>" +
                    "<td class='i0' align='right'>&nbsp;102.5</td>" +
                    "<td class='i0' align='right'>&nbsp;124.1</td>" +
                    "<td class='i0' align='right'>&nbsp;250.4</td>" +
                    "</tr></tbody>test";

    @Test
    public void transformData() {
        Mockito.when(skiJumperService.findOneByName(Mockito.anyString())).thenReturn(Optional.of(new SkiJumperDTO().id(1L)));

        Long raceDataId = 1l;
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultDataSynchronize jumpResultDataSynchronize = new JumpResultDataSynchronize(diagnosticMonitorMock, skiJumperService, raceDataId);
        List<JumpResultDTO> jumpResultDTOS = jumpResultDataSynchronize.transformData(words);
        Assertions.assertThat(jumpResultDTOS).isNotEmpty();
        Assertions.assertThat(jumpResultDTOS).hasSize(1);

        JumpResultDTO expectedJumpResultDTO = new JumpResultDTO()
                .rank(1)
                .jumperId(1L)
                .dataRaceId(raceDataId)
                .firstJump(99.0)
                .pointsForFirstJump(126.3)
                .secondJump(102.5)
                .pointsForSecondJump(124.1)
                .totalPoints(250.4);

        expectedJumpResultDTO.setId(jumpResultDTOS.get(0).getId());
        Assertions.assertThat(jumpResultDTOS.get(0)).isEqualToComparingFieldByFieldRecursively(expectedJumpResultDTO);
    }
}
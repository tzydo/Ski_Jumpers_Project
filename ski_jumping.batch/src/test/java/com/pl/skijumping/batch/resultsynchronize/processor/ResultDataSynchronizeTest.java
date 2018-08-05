package com.pl.skijumping.batch.resultsynchronize.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class ResultDataSynchronizeTest {
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
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        ResultDataSynchronize resultDataSynchronize = new ResultDataSynchronize(diagnosticMonitorMock);
        List<SkiJumperDTO> skiJumperDTOList = resultDataSynchronize.transformData(words);
        Assertions.assertThat(skiJumperDTOList).isNotEmpty();
        Assertions.assertThat(skiJumperDTOList).hasSize(1);

        SkiJumperDTO expectedSkiJumperDTO = new SkiJumperDTO()
                .bib(46)
                .fisCode(6198)
                .name("TAKANASHI Sara")
                .nationality("JPN");

        Assertions.assertThat(skiJumperDTOList).hasSize(1);
        expectedSkiJumperDTO.setId(skiJumperDTOList.get(0).getId());
        Assertions.assertThat(skiJumperDTOList.get(0)).isEqualToComparingFieldByFieldRecursively(expectedSkiJumperDTO);
    }
}
package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindCompetitionsDataTest {

    @Mock
    DiagnosticMonitor diagnosticMonitor;

    private final String testWords =
            "<h6>Planica</h6>" +
                    "<div><p>World Cup</p><p>Ski Jumping</p><p>Men's HS240</p>" +
                    "</div></div>";

    @Test
    public void setValueTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FindCompetitionsData findCompetitionsData = new FindCompetitionsData(testWords, diagnosticMonitor);
        findCompetitionsData.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isNotNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isEqualTo("Men's HS240");
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isNotNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isEqualTo("World Cup");
    }

    @Test
    public void setValueWhenNotFoundTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FindCompetitionsData findCompetitionsData = new FindCompetitionsData("<h6>Planica</h6>div><p>", diagnosticMonitor);
        findCompetitionsData.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isNull();
    }

    @Test
    public void setValueWhenFoundOnlyOneTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FindCompetitionsData findCompetitionsData = new FindCompetitionsData(
                "<h6>Planica</h6>div><p>World Cup</p>", diagnosticMonitor);
        findCompetitionsData.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isEqualTo("World Cup");
    }
}
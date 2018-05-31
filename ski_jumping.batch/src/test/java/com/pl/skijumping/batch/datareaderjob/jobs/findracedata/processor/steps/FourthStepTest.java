package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.processor.steps;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FourthStepTest {

    @Mock
    DiagnosticMonitor diagnosticMonitor;

    private final String testWords =
            "<h6>Planica</h6>" +
                    "<div><p>World Cup</p><p>Ski Jumping</p><p>Men's HS240</p>" +
                    "</div></div>";

    @Test
    public void setValueTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FourthStep fourthStep = new FourthStep(testWords, diagnosticMonitor);
        fourthStep.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isNotNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isEqualTo("World Cup");
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isNotNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isEqualTo("Men's HS240");
    }

    @Test
    public void setValueWhenNotFoundTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FourthStep fourthStep = new FourthStep("<h6>Planica</h6>div><p>", diagnosticMonitor);
        fourthStep.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isNull();
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isNull();
    }

    @Test
    public void setValueWhenFoundOnlyOneTest() {
        DataRaceDTO dataRaceDTO = new DataRaceDTO();

        FourthStep fourthStep = new FourthStep(
                "<h6>Planica</h6>div><p>World Cup</p>", diagnosticMonitor);
        fourthStep.setValue(dataRaceDTO);

        Assertions.assertThat(dataRaceDTO.getCompetitionName()).isEqualTo("World Cup");
        Assertions.assertThat(dataRaceDTO.getCompetitionType()).isNull();
    }
}
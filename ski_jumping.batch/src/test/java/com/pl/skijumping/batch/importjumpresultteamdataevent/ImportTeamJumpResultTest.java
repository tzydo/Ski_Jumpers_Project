package com.pl.skijumping.batch.importjumpresultteamdataevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.importjumpresultteamdataevent.ImportTeamJumpResult;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.service.JumpResultService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

public class ImportTeamJumpResultTest {

    @Test
    public void importTeamDataTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultService mockJumpResultService = Mockito.mock(JumpResultService.class);
        Mockito.when(mockJumpResultService.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(mockJumpResultService.findByJumpResult(Mockito.any())).thenReturn(Optional.empty());

        ImportTeamJumpResult importTeamJumpResult = new ImportTeamJumpResult(diagnosticMonitorMock, mockJumpResultService);
        String fileName = "team_jump_result.txt";
        Set<JumpResultDTO> jumpResultDTOS = importTeamJumpResult.importTeamData(Paths.get(new ClassPathResource(fileName).getURI()).toString());
        Assertions.assertThat(jumpResultDTOS).hasSize(40);
    }

    @Test
    public void importTeamDataSecondTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultService mockJumpResultService = Mockito.mock(JumpResultService.class);
        Mockito.when(mockJumpResultService.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(mockJumpResultService.findByJumpResult(Mockito.any())).thenReturn(Optional.empty());

        ImportTeamJumpResult importTeamJumpResult = new ImportTeamJumpResult(diagnosticMonitorMock, mockJumpResultService);
        String fileName = "importTeamResult.txt";
        Set<JumpResultDTO> jumpResultDTOS = importTeamJumpResult.importTeamData(Paths.get(new ClassPathResource(fileName).getURI()).toString());
        Assertions.assertThat(jumpResultDTOS).hasSize(40);
    }
}
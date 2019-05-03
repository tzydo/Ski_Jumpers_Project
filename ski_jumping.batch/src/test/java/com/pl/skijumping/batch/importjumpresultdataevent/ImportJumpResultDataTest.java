package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.JumpResultService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;

public class ImportJumpResultDataTest {

    private static final String FILE_PATH = "jump_result.txt";

    @Test
    public void importData() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        JumpResultService mockJumpResultService = Mockito.mock(JumpResultService.class);
        ImportJumpResultData importJumpResultData = new ImportJumpResultData(diagnosticMonitorMock, mockJumpResultService);
        importJumpResultData.importData(Paths.get(new ClassPathResource(FILE_PATH).getURI()).toString());
    }
}
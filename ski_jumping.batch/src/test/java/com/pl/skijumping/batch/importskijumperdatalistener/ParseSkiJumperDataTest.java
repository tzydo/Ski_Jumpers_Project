package com.pl.skijumping.batch.importskijumperdatalistener;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.importskijumperdataevent.ParseSkiJumperData;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ParseSkiJumperDataTest {

    private final String fileName = "skiJumperData.txt";

    @Test
    public void importData() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        ParseSkiJumperData parseSkiJumperData = new ParseSkiJumperData(diagnosticMonitorMock);
        JumpResultDTO jumpResultDTO = new JumpResultDTO().jumperName("test").fisCodeId(1234);
        SkiJumperDTO skiJumperDTO = parseSkiJumperData.importData(jumpResultDTO, Paths.get(new ClassPathResource(fileName).getURI()).toString());


        SkiJumperDTO expectedJumperDTO = new SkiJumperDTO()
                .fisCode(1234)
                .name("test")
                .birthday(LocalDate.of(1990, 3, 12))
                .nationality("Poland")
                .team("TS Wisla Zakopane")
                .gender("Male")
                .martialStatus("In a relationship");
        Assertions.assertThat(skiJumperDTO).isEqualToComparingFieldByFieldRecursively(expectedJumperDTO);
    }
}
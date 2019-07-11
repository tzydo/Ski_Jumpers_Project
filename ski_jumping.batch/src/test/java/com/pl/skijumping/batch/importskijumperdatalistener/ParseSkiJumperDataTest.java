package com.pl.skijumping.batch.importskijumperdatalistener;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.batch.importskijumperdataevent.ParseSkiJumperData;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.model.Gender;
import com.pl.skijumping.domain.model.MaritalStatus;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ParseSkiJumperDataTest {

    private static final String FILE_NAME = "skiJumperData.txt";

    @Test
    public void importDataTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        ParseSkiJumperData parseSkiJumperData = new ParseSkiJumperData(diagnosticMonitorMock);
        JumpResultDTO jumpResultDTO = new JumpResultDTO().jumperName("test").fisCodeId(1234);
        SkiJumperDTO skiJumperDTO = parseSkiJumperData.importData(jumpResultDTO, Paths.get(new ClassPathResource(FILE_NAME).getURI()).toString());


        SkiJumperDTO expectedJumperDTO = new SkiJumperDTO()
                .fisCode(1234)
                .name("test")
                .birthday(LocalDate.of(1990, 3, 12))
                .nationality("Poland")
                .team("TS Wisla Zakopane")
                .gender(Gender.MALE.name())
                .martialStatus(MaritalStatus.IN_RELATIONSHIP.name());
        Assertions.assertThat(skiJumperDTO).isEqualToComparingFieldByFieldRecursively(expectedJumperDTO);
    }

    @Test
    public void importDataFullTest() throws IOException {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        ParseSkiJumperData parseSkiJumperData = new ParseSkiJumperData(diagnosticMonitorMock);
        JumpResultDTO jumpResultDTO = new JumpResultDTO().jumperName("test").fisCodeId(7726);
        SkiJumperDTO skiJumperDTO = parseSkiJumperData.importData(jumpResultDTO, Paths.get(new ClassPathResource("ski_jumper__7726.txt").getURI()).toString());


        SkiJumperDTO expectedJumperDTO = new SkiJumperDTO()
                .fisCode(7726)
                .name("test")
                .birthday(LocalDate.of(2002, 1, 1))
                .nationality("Russia")
                .team("Saint-Petersburg Sdusshor")
                .gender(Gender.FEMALE.name())
                .martialStatus(MaritalStatus.UNDEFINED.name());
        Assertions.assertThat(skiJumperDTO).isEqualToComparingFieldByFieldRecursively(expectedJumperDTO);
    }
}
package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.batch.SetupUtilTests;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class NameConverterTest {

    @Test
    public void convertTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        String words = "FORFANG Johann Andre";

        NameConverter nameConverter = new NameConverter(diagnosticMonitorMock);
        String actualName = nameConverter.convert(words);

        String expectedName = "Johann Andre Forfang";
        Assertions.assertThat(actualName).isNotEmpty();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }

    @Test
    public void convertWhenFewPartsTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        String words = "fifth first second third fourth";

        NameConverter nameConverter = new NameConverter(diagnosticMonitorMock);
        String actualName = nameConverter.convert(words);

        String expectedName = "First Second Third Fourth Fifth";
        Assertions.assertThat(actualName).isNotEmpty();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }

    @Test
    public void convertWhenNullTest() {
        DiagnosticMonitor diagnosticMonitorMock = SetupUtilTests.getDiagnosticMonitorMock();
        NameConverter nameConverter = new NameConverter(diagnosticMonitorMock);
        Assertions.assertThat(nameConverter.convert(null)).isNull();
    }
}
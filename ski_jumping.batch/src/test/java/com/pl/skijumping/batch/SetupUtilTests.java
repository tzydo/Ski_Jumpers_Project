package com.pl.skijumping.batch;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;

public class SetupUtilTests {
    public static final DiagnosticMonitor getDiagnosticMonitorMock() {
        DiagnosticMonitor mock = Mockito.mock(DiagnosticMonitor.class);
        doNothing().when(mock).logInfo(anyString());
        doNothing().when(mock).logWarn(anyString());
        doNothing().when(mock).logError(anyString(), any());
        return mock;
    }
}

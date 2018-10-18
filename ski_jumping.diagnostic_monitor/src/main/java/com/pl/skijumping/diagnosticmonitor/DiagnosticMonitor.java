package com.pl.skijumping.diagnosticmonitor;

public interface DiagnosticMonitor {
    void logError(String errorMessage, Class<?> classType);
    void logInfo(String infoMessage);
    void logWarn(String warnMessage);
}

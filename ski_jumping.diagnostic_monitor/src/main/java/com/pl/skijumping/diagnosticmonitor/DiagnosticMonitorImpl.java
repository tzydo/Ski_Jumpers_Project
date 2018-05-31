package com.pl.skijumping.diagnosticmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DiagnosticMonitorImpl implements DiagnosticMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticMonitorImpl.class);
    private final SlackCommunicator slackCommunicator;

    public DiagnosticMonitorImpl() {
        slackCommunicator = new SlackCommunicator();
    }

    @Override
    public void logError(String errorMessage, Class<?> classType) {
        String message = String.format("Error in class %s, message: \n", classType.getName());
        LOGGER.error(message);
        slackCommunicator.sendMessage(message);
    }

    @Override
    public void logInfo(String infoMessage) {
        LOGGER.info(infoMessage);
    }
}

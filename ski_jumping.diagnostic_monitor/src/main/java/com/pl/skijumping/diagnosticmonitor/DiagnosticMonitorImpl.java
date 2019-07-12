package com.pl.skijumping.diagnosticmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiagnosticMonitorImpl implements DiagnosticMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticMonitorImpl.class);
    private final SlackClient slackClient;

//    public DiagnosticMonitorImpl(@Value("${skijumping.slack.url}") Optional<String> slackUrl ) {
    public DiagnosticMonitorImpl(@Value("${skijumping.slack.url}") String slackUrl ) {
//        if(slackUrl.isPresent())
        slackClient = new SlackClient(slackUrl);
    }

    @Override
    public void logError(String errorMessage, Class<?> classType) {
        String message = String.format("Error in class %s, message: \n %s", classType.getSimpleName(), errorMessage);
        LOGGER.error(message);
        slackClient.sendMessage(message);
    }

    @Override
    public void logInfo(String infoMessage) {
        LOGGER.info(infoMessage);
    }

    @Override
    public void logWarn(String warnMessage) {
        LOGGER.warn(warnMessage);
    }
}

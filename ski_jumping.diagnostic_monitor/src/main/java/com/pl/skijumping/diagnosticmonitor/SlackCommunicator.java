package com.pl.skijumping.diagnosticmonitor;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;

import java.io.IOException;

class SlackCommunicator {
    private static final String URL = "https://hooks.slack.com/services/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";
    public SlackCommunicator() {
    }

    public void sendMessage(String message) {
        Slack slack = Slack.getInstance();
        try {
            slack.send(URL, createPayload(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payload createPayload(String message) {
        return Payload.builder()
                .channel("#general")
                .username("slackbot")
                .iconEmoji(":smile_cat:")
                .text(message)
                .build();
    }

}
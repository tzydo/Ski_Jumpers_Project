package com.pl.skijumping.diagnosticmonitor;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;

import java.io.IOException;

class SlackClient {
    private final Slack slack;
    private final String url;

    public SlackClient(String url) {
        slack = Slack.getInstance();
        this.url = url;
    }

    public void sendMessage(String message) {
        try {
            slack.send(url, createPayload(message));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
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
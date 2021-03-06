package com.pl.skijumping.batch.importjumpresultdataevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.common.util.BasicDataParser;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

class JumpResultParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(JumpResultParser.class);
    private final DiagnosticMonitor diagnosticMonitor;

    JumpResultParser(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    JumpResultDTO parseData(String templateData) {
        if( templateData == null || templateData.isEmpty()) {
            return null;
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Set<String> jumpResultPoints = matchingWords.getJumpResultPoints(templateData);
        Iterator<String> iterator = jumpResultPoints.iterator();

        try {
            return new JumpResultDTO()
                    .rank(BasicDataParser.toInt(matchingWords.getJumpResultJumperRank(templateData)))
                    .totalPoints(BasicDataParser.toDouble(matchingWords.getJumpResultJumperTotal(templateData)))
                    .firstJump(getValueFromList(iterator))
                    .pointsForFirstJump(getValueFromList(iterator))
                    .secondJump(getValueFromList(iterator))
                    .pointsForSecondJump(getValueFromList(iterator))
                    .fisCodeId(BasicDataParser.toInt(matchingWords.getJumpResultFisCode(templateData)))
                    .competitorId(matchingWords.getJumpResultCompetitorId(templateData))
                    .jumperName(NameConverter.convert(matchingWords.getJumpResultJumperName(templateData)));
        }catch (NullPointerException e) {
            LOGGER.error("Cannot parse data to jump result: {}", templateData);
            return null;
        }
    }

    private Double getValueFromList(Iterator<String> iterator) {
        if (iterator == null) {
            return 0.0;
        }

        if (iterator.hasNext()) {
            return BasicDataParser.toDouble(iterator.next());
        } else {
            return 0.0;
        }
    }
}
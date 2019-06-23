package com.pl.skijumping.batch.importjumpresultdataevent.team;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.matchingword.RegexpPattern;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.JumpResultDTO;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class TeamJumpResultParser {
    private static final String REPLACE_TO = "JUMP_RESULT_";
    private final DiagnosticMonitor diagnosticMonitor;

    TeamJumpResultParser(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    Set<JumpResultDTO> parseData(String template) {
        if (template == null || template.isEmpty()) {
            return new HashSet<>();
        }

        String replacedWord = JumpResultTeamFinderUtil.replaceByPattern(template, RegexpPattern.JUMP_RESULT_DATA, REPLACE_TO);
        int numberOfReplace = replacedWord.split(REPLACE_TO, -1).length - 1;
        if (numberOfReplace < 0 || numberOfReplace % 5 != 0) {
            throw new IllegalStateException(String.format("Incorrect jumper list size, current size: %d", numberOfReplace));
        }

        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Set<String> jumpResultGroups = JumpResultTeamFinderUtil.divideIntoPartsByPattern(replacedWord, REPLACE_TO);
        return jumpResultGroups.stream()
                .filter(Objects::nonNull)
                .map(value -> matchJumpResult(matchingWords, value))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Set<JumpResultDTO> matchJumpResult(MatchingWords matchingWords, String template) {
        return matchingWords.getTeamJumpResults(template).stream()
                .map(value -> ResultParser.parseJumpResult(matchingWords, value))
                .collect(Collectors.toSet());
    }
}

package com.pl.skijumping.batch.matchingword;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;

import java.util.HashSet;
import java.util.Set;

public class MatchingWords {
    private final DiagnosticMonitor diagnosticMonitor;

    public MatchingWords(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    //    ---------------   IMPORT EVENT ---------------

    public Set<String> getEventIds(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.EVENT_ID, false, 1);
    }

//    ---------------   RACE DATA ---------------

    public Set<String> getRaceDataTemplate(String words) {
        if (isEmpty(words)) return new HashSet<>();
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_TEMPLATE, false, 1);
    }

    public String getRaceDataIdRace(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_RACE_ID, false, 1).stream().findFirst().orElse(null);
    }

    public Boolean checkRaceDataIsCancelled(String words) {
        if (isEmpty(words)) return false;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        String cancelled = findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_CHECK_CANCELLED, false, 1).stream().findFirst().orElse(null);
        return cancelled == null || cancelled.isEmpty();
    }

    public String getRaceDataDate(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_DATE, false, 1).stream().findFirst().orElse(null);
    }
    public String getRaceDataDateSecond(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_DATE_SECOND_OPT, false, 1).stream().findFirst().orElse(null);
    }

    public String getRaceDataJumpCategoryShortName(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_CATEGORY, false, 1).stream().findFirst().orElse(null);
    }

    public String getRaceDataCodex(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_CODEX, false, 1).stream().findFirst().orElse(null);
    }
    public String getRaceDataCodexSecondOpt(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_CODEX_SECOND, false, 1).stream().findFirst().orElse(null);
    }

    public String getRaceDataGender(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_GENDER, false, 1).stream().findFirst().orElse(null);
    }

    public String getRaceDataCompetitionType(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_HILL_SIZE, false, 1).stream().findFirst().orElse(null);
    }

    //return ex. Oberstdorf(GER)
    public String getRaceDataCity(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_RACE_DATA_GET_CITY, false, 1).stream().findFirst().orElse(null);
    }

//    ---------------   JUMP RESULT ---------------

    public Set<String> getJumpResultTemplate(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_TEMPLATE, false, 1);
    }

    public Set<String> getJumpResultPoints(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_POINTS, false, 1);
    }

    public String getJumpResultFisCode(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_FIS_CODE, false, 1).stream().findFirst().orElse(null);
    }

    public String getJumpResultJumperName(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_JUMPER_NAME, false, 1).stream().findFirst().orElse(null);
    }

    public String getJumpResultJumperRank(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_RANK, false, 1).stream().findFirst().orElse(null);
    }

    public String getJumpResultJumperTotal(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_TOTAL, false, 1).stream().findFirst().orElse(null);
    }

    public String getJumpResultCompetitorId(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_COMPETITOR_ID, false, 1).stream().findFirst().orElse(null);
    }

    public String getJumpResultTeamTotalPoints(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_JUMP_RESULT_TEAM_TOTAL, false, 1).stream().findFirst().orElse(null);
    }

//    ---------------   TEAM JUMP RESULT  ---------------

    public Set<String> getTeamJumpResultGroups(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_TEAM_JUMP_RESULT_GROUPS, false, 1);
    }

    public Set<String> getTeamJumpResults(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_TEAM_JUMP_RESULT, false, 0);
    }

    public String getTeamRankId(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_TEAM_JUMP_RESULT_RANK, false, 1).stream().findFirst().orElse(null);
    }

    public String getTeamFisCodeId(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_TEAM_FIS_CODE, false, 1).stream().findFirst().orElse(null);
    }

//    ---------------   SKI JUMPER  ---------------

    public String getSkiJumperBirthDay(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_SKI_JUMPER_BIRTHDAY, false, 3).stream().findFirst().orElse(null);
    }

    public String getSkiJumperGender(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_SKI_JUMPER_GENDER, false, 3).stream().findFirst().orElse(null);
    }

    public String getSkiJumperMartialStatus(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_SKI_JUMPER_MARTIAL_STATUS, false, 3).stream().findFirst().orElse(null);
    }

    public String getSkiJumperTeam(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_SKI_JUMPER_TEAM, false, 1).stream().findFirst().orElse(null);
    }

    public String getSkiJumperCountry(String words) {
        if (isEmpty(words)) return null;
        FindMatchingWords findMatchingWords = new FindMatchingWords(diagnosticMonitor);
        return findMatchingWords.getMatchingWords(words, RegexpPattern.FIND_SKI_JUMPER_COUNTRY, false, 1).stream().findFirst().orElse(null);
    }

    private boolean isEmpty(String words) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words from null", getClass());
            return true;
        }
        return false;
    }
}

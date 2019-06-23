package com.pl.skijumping.batch.importdataraceevent.reader;

import com.pl.skijumping.batch.importdataraceevent.FindRaceDataUtil;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.util.BasicDataParser;
import com.pl.skijumping.batch.util.ValueChecker;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.JumpCategoryService;

public class FindRaceData {
    private final DiagnosticMonitor diagnosticMonitor;
    private final Integer tournamentYear;
    private final String tournamentEventId;
    private final JumpCategoryService jumpCategoryService;

    public FindRaceData(DiagnosticMonitor diagnosticMonitor,
                        Integer tournamentYear,
                        String tournamentEventId,
                        JumpCategoryService jumpCategoryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.tournamentYear = tournamentYear;
        this.tournamentEventId = tournamentEventId;
        this.jumpCategoryService = jumpCategoryService;
    }

    public DataRaceDTO generateRaceData(String words) {
        if (words == null || words.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot generate object DataRaceDTO from null");
            return null;
        }

        DataRaceDTO dataRaceDTO = new DataRaceDTO();
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);

        return dataRaceDTO
                .raceId(ValueChecker.isNull(BasicDataParser.parseLong(matchingWords.getRaceDataIdRace(words)), "Race id cannot be null!"))
                .isCancelled(ValueChecker.isNull(matchingWords.checkRaceDataIsCancelled(words), "Cannot match information about cancelled race"))
                .jumpCategoryId(ValueChecker.isNull(findJumpCategory(words, matchingWords), "Cannot match jump category"))
                .codex(ValueChecker.isNull(findCodex(words, matchingWords), "Cannot match codex"))
                .eventId(ValueChecker.isNull(BasicDataParser.parseLong(tournamentEventId), "Event id cannot be null!"))
                .gender(ValueChecker.isNull(matchingWords.getRaceDataGender(words), "Cannot match gender status"))
                .competitionType(ValueChecker.isNull(matchingWords.getRaceDataCompetitionType(words), "Not found competition type"))
                .seasonCode(ValueChecker.isNull(tournamentYear, "Season code cannot be null!"))
                .date(ValueChecker.isNull(FindRaceDataUtil.generateDate(findDate(words, matchingWords), Integer.toString(tournamentYear - 1)), "Date cannot be null"));
    }

    private String findCodex(String words, MatchingWords matchingWords) {
        String codex = matchingWords.getRaceDataCodex(words);
        if (codex == null) {
            return matchingWords.getRaceDataCodexSecondOpt(words);
        }
        return codex;
    }

    private String findDate(String words, MatchingWords matchingWords) {
        String raceDataDate = matchingWords.getRaceDataDate(words);
        if (raceDataDate == null) {
            return matchingWords.getRaceDataDateSecond(words);
        }

        return raceDataDate;
    }

    private Integer findJumpCategory(String words, MatchingWords matchingWords) {
        if (words == null || words.isEmpty()) {
            return null;
        }

        String shortName = matchingWords.getRaceDataJumpCategoryShortName(words);
        JumpCategoryDTO jumpCategoryDTO = this.jumpCategoryService.findByShortName(shortName)
                .orElseThrow(() -> new IllegalStateException(String.format("Not found jump category with short name: %s", shortName)));

        return jumpCategoryDTO.getId();
    }
}

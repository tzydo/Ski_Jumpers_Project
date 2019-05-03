package com.pl.skijumping.batch.importdataraceevent.reader;

import com.pl.skijumping.batch.importdataraceevent.FindRaceDataUtil;
import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.JumpCategoryService;

import java.util.Optional;

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
                .raceId(parseLong(matchingWords.getRaceDataIdRace(words)))
                .isCancelled(matchingWords.checkRaceDataIsCancelled(words))
                .jumpCategoryId(findJumpCategory(words, matchingWords))
                .codex(matchingWords.getRaceDataCodex(words))
                .eventId(parseLong(this.tournamentEventId))
                .gender(matchingWords.getRaceDataGender(words))
                .competitionType(matchingWords.getRaceDataCompetitionType(words))
                .seasonCode(tournamentYear)
                .date(FindRaceDataUtil.generateDate(matchingWords.getRaceDataDate(words), Integer.toString(tournamentYear - 1)));
    }

    private Integer findJumpCategory(String words, MatchingWords matchingWords) {
        if (words == null || words.isEmpty()) {
            return null;
        }

        String shortName = matchingWords.getRaceDataJumpCategoryShortName(words);
        Optional<JumpCategoryDTO> jumpCategoryDTO = this.jumpCategoryService.findByShortName(shortName);
        return jumpCategoryDTO.map(JumpCategoryDTO::getId).orElse(null);
    }

    private Long parseLong(String word) {
        if (word == null || word.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(word);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

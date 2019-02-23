package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.JumpCategoryDTO;
import com.pl.skijumping.service.JumpCategoryService;

import java.util.Optional;

class FindRaceData {
    private final DiagnosticMonitor diagnosticMonitor;
    private final String tournamentYear;
    private final String tournamentEventId;
    private final JumpCategoryService jumpCategoryService;

    public FindRaceData(DiagnosticMonitor diagnosticMonitor,
                        String tournamentYear,
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

        dataRaceDTO.setRaceId(parseLong(matchingWords.getRaceDataIdRace(words)));
        dataRaceDTO.setIsCancelled(matchingWords.checkRaceDataIsCancelled(words));
        dataRaceDTO.setJumpCategoryId(findJumpCategory(words, matchingWords));
        dataRaceDTO.setCodex(matchingWords.getRaceDataCodex(words));
        dataRaceDTO.setEventId(parseLong(this.tournamentEventId));
        dataRaceDTO.setGender(matchingWords.getRaceDataGender(words));
        dataRaceDTO.setCompetitionType(matchingWords.getRaceDataCompetitionType(words));

        int year = Integer.parseInt(tournamentYear);
        dataRaceDTO.setSeasonCode(year);
        dataRaceDTO.setDate(FindRaceDataUtil.generateDate(matchingWords.getRaceDataDate(words), Integer.toString(year -1)));

        return dataRaceDTO;
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

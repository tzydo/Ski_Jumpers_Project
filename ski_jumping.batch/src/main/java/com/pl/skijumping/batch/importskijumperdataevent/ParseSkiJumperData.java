package com.pl.skijumping.batch.importskijumperdataevent;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.batch.reader.DataReader;
import com.pl.skijumping.common.util.BasicDataParser;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.model.Gender;
import com.pl.skijumping.domain.model.MaritalStatus;
import com.pl.skijumping.dto.JumpResultDTO;
import com.pl.skijumping.dto.SkiJumperDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParseSkiJumperData {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DiagnosticMonitor diagnosticMonitor;

    public ParseSkiJumperData(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public SkiJumperDTO importData(JumpResultDTO jumpResultDTO, String filePath) {
        if (jumpResultDTO == null || filePath == null || filePath.isEmpty()) {
            return null;
        }

        DataReader dataReader = new DataReader(diagnosticMonitor);
        String readSource = dataReader.read(filePath);
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        return new SkiJumperDTO()
                .fisCode(jumpResultDTO.getFisCodeId())
                .name(jumpResultDTO.getJumperName())
                .birthday(parseDate(matchingWords, readSource))
                .nationality(matchingWords.getSkiJumperCountry(readSource))
                .team(matchingWords.getSkiJumperTeam(readSource))
                .gender(Gender.getEnum(matchingWords.getSkiJumperGender(readSource)).name())
                .martialStatus(MaritalStatus.getEnum(matchingWords.getSkiJumperMartialStatus(readSource)).name());
    }

    private LocalDate parseDate(MatchingWords matchingWords, String readSource) {
        String skiJumperBirthDay = matchingWords.getSkiJumperBirthDay(readSource);
        if(skiJumperBirthDay == null) {
            return null;
        }
        try {
            return LocalDate.parse(skiJumperBirthDay, DATE_TIME_FORMATTER);
        }catch (DateTimeParseException e) {
            return LocalDate.of(BasicDataParser.toInt(skiJumperBirthDay), 1,1);
        }
    }
}

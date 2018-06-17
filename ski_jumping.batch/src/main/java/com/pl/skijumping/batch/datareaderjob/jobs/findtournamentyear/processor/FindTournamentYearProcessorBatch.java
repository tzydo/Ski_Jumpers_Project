package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTournamentYearProcessorBatch implements ItemProcessor<String, List<String>> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindTournamentYearProcessorBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public List<String> process(String fileContent) throws InternalServiceException {
        diagnosticMonitor.logInfo("Searching for matches tournament years");
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words, file is empty", getClass());
            return new ArrayList<>();
        }
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        Optional<List<String>> tournamentYears = matchingWords.getTournamentYears(fileContent);
        if (!tournamentYears.isPresent() || tournamentYears.get().isEmpty()) {
            String errorMessage = "Not found any matching words in FindTournamentYearJob.";
            diagnosticMonitor.logError(errorMessage, getClass());
            throw new InternalServiceException(errorMessage);
        }

        diagnosticMonitor.logInfo(String.format("Found %d matching tournament years", tournamentYears.get().size()));
        return tournamentYears.get();
    }
}

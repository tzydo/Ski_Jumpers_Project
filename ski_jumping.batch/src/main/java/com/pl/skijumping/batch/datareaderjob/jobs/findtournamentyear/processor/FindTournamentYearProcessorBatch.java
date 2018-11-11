package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FindTournamentYearProcessorBatch implements ItemProcessor<String, List<String>> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindTournamentYearProcessorBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public List<String> process(String fileContent) {
        diagnosticMonitor.logInfo("Searching for matches tournament years");
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words, file is empty", getClass());
            return new ArrayList<>();
        }
        MatchingWords matchingWords = new MatchingWords(diagnosticMonitor);
        String years = matchingWords.getTournamentYears(fileContent)
                .orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(" "));

        List<String> tournamentYears = matchingWords.getTournamentYearsFilterData(years).orElse(new ArrayList<>());

        if (tournamentYears.isEmpty()) {
            String errorMessage = "Not found any matching words in FindTournamentYearJob.";
            diagnosticMonitor.logError(errorMessage, getClass());
        }

        diagnosticMonitor.logInfo(String.format("Found %d matching tournament years", tournamentYears.size()));
        return tournamentYears;
    }
}

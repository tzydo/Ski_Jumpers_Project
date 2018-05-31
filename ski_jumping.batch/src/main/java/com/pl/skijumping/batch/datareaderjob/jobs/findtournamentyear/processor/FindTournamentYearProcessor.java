package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.processor;

import com.pl.skijumping.batch.datareaderjob.reader.matchingword.MatchingWords;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindTournamentYearProcessor implements ItemProcessor<String, List<String>> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindTournamentYearProcessor(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public List<String> process(String fileContent) throws Exception {
        diagnosticMonitor.logInfo("Searching for matches tournament years");
        if (fileContent == null || fileContent.isEmpty()) {
            diagnosticMonitor.logError("Cannot find matching words, file is empty", getClass());
            return new ArrayList<>();
        }
        MatchingWords matchingWords = new MatchingWords();
        Optional<List<String>> tournamentYears = matchingWords.getTournamentYears(fileContent);
        if (!tournamentYears.isPresent() || tournamentYears.get().isEmpty()) {
            diagnosticMonitor.logError("Not found any matching words.", getClass());
            return new ArrayList<>();
        }

        diagnosticMonitor.logInfo(String.format("Found %d matching tournament years", tournamentYears.get().size()));
        return tournamentYears.get();
    }
}

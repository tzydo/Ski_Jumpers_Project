package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.service.TournamentYearService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.Objects;

public class FindTournamentYearWriter implements ItemWriter<List<String>> {
    private final TournamentYearService tournamentYearService;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindTournamentYearWriter(TournamentYearService tournamentYearService,
                                    DiagnosticMonitor diagnosticMonitor) {
        this.tournamentYearService = tournamentYearService;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public void write(List<? extends List<String>> yearList) {
        if (yearList == null || yearList.isEmpty() || yearList.get(0).isEmpty()) {
            diagnosticMonitor.logError("Cannot save empty tournament year list", getClass());
            return;
        }
        List<String> tournamentYearDTOList = yearList.get(0);
        tournamentYearDTOList.stream()
                .filter(Objects::nonNull)
                .forEach(this::saveYear);

        diagnosticMonitor.logInfo("Finish saving tournament years to database");
    }

    private TournamentYearDTO saveYear(String year) {
        return tournamentYearService.save(new TournamentYearDTO(null, year)).get();
    }
}
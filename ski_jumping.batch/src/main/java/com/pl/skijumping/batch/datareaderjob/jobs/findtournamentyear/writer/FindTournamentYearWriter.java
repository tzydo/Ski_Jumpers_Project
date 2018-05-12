package com.pl.skijumping.batch.datareaderjob.jobs.findtournamentyear.writer;

import com.pl.skijumping.domain.dto.TournamentYearDTO;
import com.pl.skijumping.service.TournamentYearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.Objects;

public class FindTournamentYearWriter implements ItemWriter<List<String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindTournamentYearWriter.class);
    private final TournamentYearService tournamentYearService;

    public FindTournamentYearWriter(TournamentYearService tournamentYearService) {
        this.tournamentYearService = tournamentYearService;
    }

    @Override
    public void write(List<? extends List<String>> yearList) {
        if (yearList == null || yearList.isEmpty() || yearList.get(0).isEmpty()) {
            LOGGER.error("Cannot save empty tournament year list");
            return;
        }
        List<String> tournamentYearDTOList = yearList.get(0);
        tournamentYearDTOList.stream()
                .filter(Objects::nonNull)
                .forEach(e -> saveYear(e));

        LOGGER.info("Finish saving tournament years to database");
    }

    private TournamentYearDTO saveYear(String year) {
        return tournamentYearService.save(new TournamentYearDTO(null, year)).get();
    }
}
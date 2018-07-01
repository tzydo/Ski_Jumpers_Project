package com.pl.skijumping.batch.datasynchronize;

import com.pl.skijumping.dto.TournamentYearDTO;
import com.pl.skijumping.service.TournamentYearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

class BasicDataHostGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataHostGenerator.class);
    private final TournamentYearService tournamentYearService;

    public BasicDataHostGenerator(TournamentYearService tournamentYearService) {
        this.tournamentYearService = tournamentYearService;
    }

    public Map<String, String> generate(String host){
        if(host == null) {
            LOGGER.warn("Cannot generate host url from null");
            return new HashMap<>();
        }

        Optional<List<TournamentYearDTO>> tournamentYearDTOList = tournamentYearService.findAll();
        if(!tournamentYearDTOList.isPresent()) {
            LOGGER.warn("Cannot generate host url from empty tournament year");
            return new HashMap<>();
        }

        return tournamentYearDTOList.get().stream()
                .distinct()
                .collect(Collectors.toMap(TournamentYearDTO::getYear, t ->host.concat(t.getYear())));
    }
}

//package com.pl.skijumping.batch.datasynchronize;
//
//import com.pl.skijumping.dto.TournamentYearDTO;
//import com.pl.skijumping.service.TournamentYearService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//class BasicDataHostGenerator {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataHostGenerator.class);
//    private final TournamentYearService tournamentYearService;
//    private final Boolean loadAllData;
//    private final Integer numberOfPreviousYear;
//
//    public BasicDataHostGenerator(TournamentYearService tournamentYearService,
//                                  Boolean loadAllData,
//                                  Integer numberOfPreviousYear) {
//        this.tournamentYearService = tournamentYearService;
//        this.loadAllData = loadAllData;
//        this.numberOfPreviousYear = numberOfPreviousYear;
//    }
//
//    public Map<String, String> generate(String host) {
//        if (host == null) {
//            LOGGER.warn("Cannot generate host url from null");
//            return new HashMap<>();
//        }
//
//        List<TournamentYearDTO> tournamentYearDTOList;
//
//        if (loadAllData) {
//            tournamentYearDTOList = tournamentYearService.findAll();
//        } else {
//            tournamentYearDTOList = tournamentYearService.findAllByTop(numberOfPreviousYear);
//        }
//
//        if (tournamentYearDTOList.isEmpty()) {
//            LOGGER.warn("Cannot generate host url from empty tournament year");
//            return new HashMap<>();
//        }
//
//        return tournamentYearDTOList.stream()
//                .distinct()
//                .collect(Collectors.toMap(TournamentYearDTO::getYear, t -> host.concat(t.getYear())));
//    }
//}

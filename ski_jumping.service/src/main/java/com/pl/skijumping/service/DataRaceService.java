//package com.pl.skijumping.service;
//
//import com.pl.skijumping.domain.dto.DataRaceDTO;
//import com.pl.skijumping.domain.entity.DataRace;
//import com.pl.skijumping.domain.entity.QCompetitionType;
//import com.pl.skijumping.domain.entity.QDataRace;
//import com.pl.skijumping.domain.repository.DataRaceRepository;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//import java.util.Optional;
//
////import com.pl.skijumping.domain.mapper.DataRaceMapper;
//
//@Service
//public class DataRaceService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DataRaceService.class);
//    private final DataRaceRepository dataRaceRepository;
////    private final DataRaceMapper dataRaceMapper;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public DataRaceService(DataRaceRepository dataRaceRepository) {
//        this.dataRaceRepository = dataRaceRepository;
////        this.dataRaceMapper = dataRaceMapper;
//    }
//
//    public List<DataRace> findAll() {
//        QDataRace qDataRace = QDataRace.dataRace;
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        return query.selectFrom(qDataRace).fetch();
//    }
//
//    public Optional<DataRaceDTO> find(DataRaceDTO dataRaceDTO){
//        if (dataRaceDTO == null) {
//            return Optional.empty();
//        }
//        QDataRace qDataRace = QDataRace.dataRace;
//        QCompetitionType qCompetitionType = QCompetitionType.competitionType1;
//
////        new JPAQuery(em).from(qDataRace)
//////                .join(qDataRace.competitionType, qCompetitionType)
////                .where(qDataRace.date.eq(dataRaceDTO.getDate())
////                        .and(qDataRace.city.eq(dataRaceDTO.getCity()))
////                        .and(qDataRace.shortCountryName.eq(dataRaceDTO.getShortCountryName()))
//////                        .and(qDataRace.competitionType.competitionName.eq(dataRaceDTO.getCompetitionName()))
//////                        .and(qDataRace.competitionType.competitionType.eq(dataRaceDTO.getCompetitionType()))
////                        .and(qDataRace.raceId.eq(dataRaceDTO.getRaceId())));
//
//        return null;
//
////    private String competitionType;
////    private Integer raceId;
//    }
//
//
//
////
////    public Optional<DataRaceDTO> save(DataRaceDTO dataRaceDTO) {
////        if (dataRaceDTO == null ) {
////            LOGGER.error("Cannot save null object!");
////            return Optional.empty();
////        }
////        TournamentYear tournamentByYear = dataRaceRepository.find(dataRaceDTO);
////        if (tournamentByYear == null) {
////            LOGGER.info(String.format("Saving new competition year: %s", tournamentYearDTO.getYear()));
////            tournamentByYear = this.tournamentYearRepository.save(
////                    tournamentYearMapper.fromDTO(tournamentYearDTO));
////        } else {
////            LOGGER.info(String.format
////                    ("The year: %s of the competition already exists in the database", tournamentYearDTO.getYear()));
////        }
////
////        return Optional.of(tournamentYearMapper.toDTO(tournamentByYear));
////    }
//}
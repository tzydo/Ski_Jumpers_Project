//package com.pl.skijumping.service;
//
//import com.pl.skijumping.domain.entity.CompetitionType;
//import com.pl.skijumping.domain.repository.CompetitionTypeRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CompetitionTypeService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionTypeService.class);
//    private final CompetitionTypeRepository competitionTypeRepository;
//
//    public CompetitionTypeService(CompetitionTypeRepository competitionTypeRepository) {
//        this.competitionTypeRepository = competitionTypeRepository;
//    }
//
//    public Optional<CompetitionType> save(CompetitionType competitionType) {
//        if(competitionType == null) {
//            LOGGER.error("Cannot save null competition type");
//            return Optional.of(competitionType);
//        }
//
//        return Optional.of(competitionTypeRepository.save(competitionType));
//    }
//
//    public Optional<CompetitionType> find(Long id) {
//        if(id == null) {
//            LOGGER.error("Cannot find competition type for null id");
//            return Optional.empty();
//        }
//
//        return Optional.of(competitionTypeRepository.findOne(id));
//    }
//
////    public Optional<CompetitionType> createOrUpdate(CompetitionType competitionType) {
////        if(competitionType == null) {
////            return Optional.empty();
////        }
////
////        return Optional.of(competitionTypeRepository.createOrUpdate(competitionType));
////    }
//}

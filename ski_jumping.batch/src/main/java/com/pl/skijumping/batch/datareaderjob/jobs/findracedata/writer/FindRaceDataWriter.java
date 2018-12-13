//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;
//
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.domain.entity.DataRace;
//import com.pl.skijumping.dto.CompetitionNameDTO;
//import com.pl.skijumping.dto.CompetitionTypeDTO;
//import com.pl.skijumping.dto.DataRaceDTO;
//import com.pl.skijumping.service.CompetitionNameService;
//import com.pl.skijumping.service.CompetitionTypeService;
//import com.pl.skijumping.service.DataRaceService;
//import com.pl.skijumping.service.mapper.CompetitionNameMapper;
//import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
//
//import java.util.List;
//import java.util.Optional;
//
//class FindRaceDataWriter {
//
//    private final CompetitionTypeService competitionTypeService;
//    private final CompetitionNameService competitionNameService;
//    private final CompetitionNameMapper competitionNameMapper;
//    private final CompetitionTypeMapper competitionTypeMapper;
//    private final DataRaceService dataRaceService;
//    private final DiagnosticMonitor diagnosticMonitor;
//
//    public FindRaceDataWriter(CompetitionTypeService competitionTypeService,
//                              CompetitionNameService competitionNameService,
//                              CompetitionTypeMapper competitionTypeMapper,
//                              CompetitionNameMapper competitionNameMapper,
//                              DataRaceService dataRaceService,
//                              DiagnosticMonitor diagnosticMonitor) {
//        this.competitionTypeService = competitionTypeService;
//        this.competitionNameService = competitionNameService;
//        this.competitionNameMapper = competitionNameMapper;
//        this.competitionTypeMapper = competitionTypeMapper;
//        this.dataRaceService = dataRaceService;
//        this.diagnosticMonitor = diagnosticMonitor;
//    }
//
//    public void write(List<DataRaceDTO> dataRaceDTOList) {
//        if(dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
//            diagnosticMonitor.logWarn("Cannot save null or empty list");
//            return;
//        }
//
//        for (DataRaceDTO dataRaceDTO : dataRaceDTOList) {
//            CompetitionTypeDTO competitionTypeDTO = getCompetitionType(dataRaceDTO.getCompetitionType());
//            CompetitionNameDTO competitionNameDTO = getCompetitionName(dataRaceDTO.getCompetitionName());
//            dataRaceService.save(create(dataRaceDTO, competitionTypeDTO, competitionNameDTO));
//        }
//    }
//
//    private DataRace create(DataRaceDTO dataRaceDTO,
//                            CompetitionTypeDTO competitionTypeId,
//                            CompetitionNameDTO competitionNameId) {
//        if(dataRaceDTO == null) {
//            return null;
//        }
//
//        return new DataRace()
//                .date(dataRaceDTO.getDate())
//                .competitionType(competitionTypeMapper.fromDTO(competitionTypeId))
//                .competitionName(competitionNameMapper.fromDTO(competitionNameId))
//                .city(dataRaceDTO.getCity())
//                .raceId(dataRaceDTO.getRaceId())
//                .shortCountryName(dataRaceDTO.getShortCountryName());
//    }
//
//    private CompetitionTypeDTO getCompetitionType(String type) {
//        if (type == null || type.isEmpty()) {
//            diagnosticMonitor.logWarn("Cannot find competitionTypeDTO for null values");
//            return null;
//        }
//
//        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByType(type);
//        return competitionTypeDTO.orElseGet(() -> competitionTypeService.save(new CompetitionTypeDTO().type(type)));
//
//    }
//
//    private CompetitionNameDTO getCompetitionName(String name) {
//        if (name == null || name.isEmpty()) {
//            diagnosticMonitor.logWarn("Cannot find competitionNameDTO for null values");
//            return null;
//        }
//
//        Optional<CompetitionNameDTO> competitionNameDTO = competitionNameService.findByName(name);
//        return competitionNameDTO.orElseGet(() -> competitionNameService.save(new CompetitionNameDTO().name(name)));
//    }
//}
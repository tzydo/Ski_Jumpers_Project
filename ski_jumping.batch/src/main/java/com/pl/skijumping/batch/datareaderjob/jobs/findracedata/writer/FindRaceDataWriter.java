package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionNameDTO;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionNameService;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;

import java.util.List;
import java.util.Optional;

class FindRaceDataWriter {

    private final CompetitionTypeService competitionTypeService;
    private final CompetitionNameService competitionNameService;
    private final DataRaceService dataRaceService;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataWriter(CompetitionTypeService competitionTypeService,
                              CompetitionNameService competitionNameService,
                              DataRaceService dataRaceService,
                              DiagnosticMonitor diagnosticMonitor) {
        this.competitionTypeService = competitionTypeService;
        this.competitionNameService = competitionNameService;
        this.dataRaceService = dataRaceService;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public void write(List<DataRaceDTO> dataRaceDTOList) {
        if(dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
            diagnosticMonitor.logError("Cannot save null or empty list", getClass());
            return;
        }

        for (DataRaceDTO dataRaceDTO : dataRaceDTOList) {
            Long competitionTypeId = getCompetitionTypeId(dataRaceDTO.getCompetitionType());
            Long competitionNameId = getCompetitionNameId(dataRaceDTO.getCompetitionName());
            dataRaceService.save(create(dataRaceDTO, competitionTypeId, competitionNameId));
        }
    }

    private DataRace create(DataRaceDTO dataRaceDTO, Long competitionTypeId, Long competitionNameId) {
        if(dataRaceDTO == null) {
            return null;
        }

        return new DataRace().builder()
                .date(dataRaceDTO.getDate())
                .competitionTypeId(competitionTypeId)
                .competitionNameId(competitionNameId)
                .city(dataRaceDTO.getCity())
                .raceId(dataRaceDTO.getRaceId())
                .shortCountryName(dataRaceDTO.getShortCountryName())
                .build();
    }

    private Long getCompetitionTypeId(String type) {
        if (type == null || type.isEmpty()) {
            diagnosticMonitor.logError("Cannot find competitionTypeDTO for null values", getClass());
            return null;
        }

        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByType(type);
        if(competitionTypeDTO.isPresent()) {
            return competitionTypeDTO.get().getId();
        }

        CompetitionTypeDTO savedCompetitionTypeDTO = CompetitionTypeDTO.builder()
                .type(type)
                .build();
        return competitionTypeService.save(savedCompetitionTypeDTO).getId();
    }

    private Long getCompetitionNameId(String name) {
        if (name == null || name.isEmpty()) {
            diagnosticMonitor.logError("Cannot find competitionNameDTO for null values", getClass());
            return null;
        }

        Optional<CompetitionNameDTO> competitionNameDTO = competitionNameService.findByName(name);
        if(competitionNameDTO.isPresent()) {
            return competitionNameDTO.get().getId();
        }

        CompetitionNameDTO savedCompetitionNameDTO = CompetitionNameDTO.builder()
                .name(name)
                .build();
        return competitionNameService.save(savedCompetitionNameDTO).getId();
    }
}
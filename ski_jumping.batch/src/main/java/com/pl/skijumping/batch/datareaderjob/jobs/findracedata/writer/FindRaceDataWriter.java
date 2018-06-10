package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.entity.DataRace;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;

import java.util.List;
import java.util.Optional;

class FindRaceDataWriter {

    private final CompetitionTypeService competitionTypeService;
    private final DataRaceService dataRaceService;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataWriter(CompetitionTypeService competitionTypeService,
                              DataRaceService dataRaceService,
                              DiagnosticMonitor diagnosticMonitor) {
        this.competitionTypeService = competitionTypeService;
        this.dataRaceService = dataRaceService;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public void write(List<DataRaceDTO> dataRaceDTOList) {
        if(dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
            diagnosticMonitor.logError("Cannot save null or empty list", getClass());
            return;
        }

        for (DataRaceDTO dataRaceDTO : dataRaceDTOList) {
            Long competitionTypeId =
                    getCompetitionTypeId(dataRaceDTO.getCompetitionType(), dataRaceDTO.getCompetitionName());
            dataRaceService.save(create(dataRaceDTO, competitionTypeId));
        }
    }

    private DataRace create(DataRaceDTO dataRaceDTO, Long competitionId) {
        if(dataRaceDTO == null) {
            return null;
        }

        return new DataRace().builder()
                .date(dataRaceDTO.getDate())
                .competitionTypeId(competitionId)
                .city(dataRaceDTO.getCity())
                .raceId(dataRaceDTO.getRaceId())
                .shortCountryName(dataRaceDTO.getShortCountryName())
                .build();
    }

    private Long getCompetitionTypeId(String type, String name) {
        if (type == null || type.isEmpty() || name == null || name.isEmpty()) {
            diagnosticMonitor.logError("Cannot find competitionTypeDTO for null values", getClass());
            return null;
        }

        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByTypeAndName(type, name);
        if(competitionTypeDTO.isPresent()) {
            return competitionTypeDTO.get().getId();
        }

        CompetitionTypeDTO savedCompetitionTypeDTO = CompetitionTypeDTO.builder()
                .competitionType(type)
                .competitionName(name)
                .build();
        return competitionTypeService.save(savedCompetitionTypeDTO).getId();
    }
}
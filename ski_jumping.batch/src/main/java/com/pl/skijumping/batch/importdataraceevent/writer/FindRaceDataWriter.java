package com.pl.skijumping.batch.importdataraceevent.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.CompetitionTypeDTO;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;

import java.util.Optional;

public class FindRaceDataWriter {

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

    public DataRaceDTO write(DataRaceDTO dataRaceDTO) {
        if (dataRaceDTO == null) {
            diagnosticMonitor.logWarn("Cannot save null or empty list");
            return null;
        }

        Optional<DataRaceDTO> existingRace = dataRaceService.findByRaceId(dataRaceDTO.getRaceId());
        if (existingRace.isPresent()) {
            return existingRace.get();
        }
        checkExistCompetitionType(dataRaceDTO.getCompetitionType());
         return dataRaceService.save(dataRaceDTO);
    }

    private void checkExistCompetitionType(String type) {
        if (type == null || type.isEmpty()) {
            diagnosticMonitor.logWarn("Cannot find competitionTypeDTO for null values");
            return;
        }

        Optional<CompetitionTypeDTO> competitionTypeDTO = competitionTypeService.findByType(type);
        competitionTypeDTO.orElseGet(() -> competitionTypeService.save(new CompetitionTypeDTO().type(type)));
    }
}
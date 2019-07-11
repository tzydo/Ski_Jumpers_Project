package com.pl.skijumping.batch.importdataraceevent;

import com.pl.skijumping.batch.importdataraceevent.reader.FindRaceData;
import com.pl.skijumping.batch.importdataraceevent.reader.FindRaceDataContent;
import com.pl.skijumping.batch.importdataraceevent.writer.FindRaceDataWriter;
import com.pl.skijumping.common.exception.InternalServiceException;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.dto.MessageDTO;
import com.pl.skijumping.dto.MessagePropertiesConst;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import com.pl.skijumping.service.JumpCategoryService;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class ImportDataRace {
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpCategoryService jumpCategoryService;
    private final CompetitionTypeService competitionTypeService;
    private final DataRaceService dataRaceService;
    private Integer tournamentYear;
    private String eventId;

    ImportDataRace(DiagnosticMonitor diagnosticMonitor,
                   MessageDTO messageDTO,
                   JumpCategoryService jumpCategoryService,
                   CompetitionTypeService competitionTypeService,
                   DataRaceService dataRaceService) {
        this.diagnosticMonitor = diagnosticMonitor;
        tournamentYear = messageDTO.getProperties().getIntValue(MessagePropertiesConst.SEASON_CODE.getValue());
        eventId = messageDTO.getProperties().getStringValue(MessagePropertiesConst.EVENT_ID.getValue());
        this.jumpCategoryService = jumpCategoryService;
        this.competitionTypeService = competitionTypeService;
        this.dataRaceService = dataRaceService;
    }

    List<DataRaceDTO> importData(Path filePath) throws InternalServiceException {
        if (filePath == null) {
            throw new InternalServiceException("Cannot read data from null path");
        }
        FindRaceDataContent findRaceDataContent = new FindRaceDataContent(this.diagnosticMonitor);
        Set<String> dataRaceTemplateData = findRaceDataContent.findTemplateData(filePath);
        FindRaceData findRaceData = new FindRaceData(diagnosticMonitor, tournamentYear, eventId, jumpCategoryService);
        FindRaceDataWriter findRaceDataWriter = new FindRaceDataWriter(this.competitionTypeService, this.dataRaceService, this.diagnosticMonitor);
        return dataRaceTemplateData.stream()
                .filter(Objects::nonNull)
                .map(findRaceData::generateRaceData)
                .filter(Objects::nonNull)
                .filter(dataRaceDTO -> !dataRaceService.findByRaceId(dataRaceDTO.getRaceId()).isPresent())
                .map(findRaceDataWriter::write)
                .collect(Collectors.toList());
    }
}

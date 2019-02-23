package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.dto.PlaceDTO;
import com.pl.skijumping.service.JumpCategoryService;

import java.nio.file.Path;
import java.util.Set;

public class FindRaceDataToPlaceProcessor {
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpCategoryService jumpCategoryService;

    public FindRaceDataToPlaceProcessor(DiagnosticMonitor diagnosticMonitor,
                                        JumpCategoryService jumpCategoryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpCategoryService = jumpCategoryService;
    }

    public Set<DataRaceToPlaceDTO> findDataRace(Path filePath) {
        FindPlaceDataContent findPlaceContent = new FindPlaceDataContent(diagnosticMonitor);
        PlaceDTO placeDTO = findPlaceContent.findData(filePath);
        FindRaceDataContent findRaceDataContent = new FindRaceDataContent(diagnosticMonitor);
        Set<String> templateData = findRaceDataContent.findTemplateData(filePath);
        String year = FindRaceDataUtil.getYearFromFilePath(filePath);
        String eventId = FindRaceDataUtil.getEventIdFromFilePath(filePath);
//        FindRaceData findRaceData = new FindRaceData(diagnosticMonitor, year, eventId, jumpCategoryService);
//
//        return templateData.stream()
//                .parallel()
//                .filter(Objects::nonNull)
//                .map(findRaceData::generateRaceData)
//                .map(dataRaceDTO -> new DataRaceToPlaceDTO().placeDTO(placeDTO).dataRaceDTO(dataRaceDTO))
//                .collect(Collectors.toSet());

        return null;
    }
}

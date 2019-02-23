package com.pl.skijumping.batch.findracedatajob.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceToPlaceDTO;
import com.pl.skijumping.service.JumpCategoryService;
import org.springframework.batch.item.ItemProcessor;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FindRaceDataToPlaceProcessorBatch implements ItemProcessor<Path, Set<DataRaceToPlaceDTO>> {
    private final DiagnosticMonitor diagnosticMonitor;
    private final JumpCategoryService jumpCategoryService;

    public FindRaceDataToPlaceProcessorBatch(DiagnosticMonitor diagnosticMonitor,
                                             JumpCategoryService jumpCategoryService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.jumpCategoryService = jumpCategoryService;
    }

    @Override
    public Set<DataRaceToPlaceDTO> process(Path filePath) {
        if (filePath == null) {
            diagnosticMonitor.logWarn("Cannot read file from null path");
            return new HashSet<>();
        }
        FindRaceDataToPlaceProcessor findRaceDataToPlaceProcessor = new FindRaceDataToPlaceProcessor(diagnosticMonitor, jumpCategoryService);
        return findRaceDataToPlaceProcessor.findDataRace(filePath);
    }
}

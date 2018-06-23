package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.dto.DataRaceDTO;
import com.pl.skijumping.service.CompetitionNameService;
import com.pl.skijumping.service.CompetitionTypeService;
import com.pl.skijumping.service.DataRaceService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class FindRaceDataWriterBatch implements ItemWriter<List<DataRaceDTO>> {
    private final CompetitionTypeService competitionTypeService;
    private final CompetitionNameService competitionNameService;
    private final DataRaceService dataRaceService;
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataWriterBatch(CompetitionTypeService competitionTypeService,
                                   CompetitionNameService competitionNameService,
                                   DataRaceService dataRaceService,
                                   DiagnosticMonitor diagnosticMonitor) {
        this.competitionTypeService = competitionTypeService;
        this.competitionNameService = competitionNameService;
        this.dataRaceService = dataRaceService;
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public void write(List<? extends List<DataRaceDTO>> dataRaceDTOList) {
        if (dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
            diagnosticMonitor.logError("Cannot save null object in find race data writer", getClass());
            return;
        }
        FindRaceDataWriter dataRaceWriter = new FindRaceDataWriter(
                competitionTypeService, competitionNameService, dataRaceService, diagnosticMonitor);
        dataRaceWriter.write(dataRaceDTOList.get(0));
    }
}

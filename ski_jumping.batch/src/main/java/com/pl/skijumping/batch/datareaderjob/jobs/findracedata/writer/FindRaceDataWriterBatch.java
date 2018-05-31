package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.domain.dto.DataRaceDTO;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class FindRaceDataWriterBatch implements ItemWriter<DataRaceDTO> {
    private final DiagnosticMonitor diagnosticMonitor;

    public FindRaceDataWriterBatch(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }
    //    private final DataRaceService dataRaceService;


    @Override
    public void write(List<? extends DataRaceDTO> dataRaceDTOList) throws Exception {
        if(dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
            diagnosticMonitor.logError("Cannot save null object in find race data writer", getClass());
            return;
        }
        return;
    }
}

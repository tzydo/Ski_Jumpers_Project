//package com.pl.skijumping.batch.datareaderjob.jobs.findracedata.writer;
//
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.dto.DataRaceDTO;
//import com.pl.skijumping.service.CompetitionNameService;
//import com.pl.skijumping.service.CompetitionTypeService;
//import com.pl.skijumping.service.DataRaceService;
//import com.pl.skijumping.service.mapper.CompetitionNameMapper;
//import com.pl.skijumping.service.mapper.CompetitionTypeMapper;
//import org.springframework.batch.item.ItemWriter;
//
//import java.util.List;
//
//public class FindRaceDataWriterBatch implements ItemWriter<List<DataRaceDTO>> {
//    private final CompetitionTypeService competitionTypeService;
//    private final CompetitionNameService competitionNameService;
//    private final DataRaceService dataRaceService;
//    private final DiagnosticMonitor diagnosticMonitor;
//    private final CompetitionNameMapper competitionNameMapper;
//    private final CompetitionTypeMapper competitionTypeMapper;
//
//    public FindRaceDataWriterBatch(CompetitionTypeService competitionTypeService,
//                                   CompetitionNameService competitionNameService,
//                                   DataRaceService dataRaceService,
//                                   DiagnosticMonitor diagnosticMonitor,
//                                   CompetitionNameMapper competitionNameMapper,
//                                   CompetitionTypeMapper competitionTypeMapper) {
//        this.competitionTypeService = competitionTypeService;
//        this.competitionNameService = competitionNameService;
//        this.dataRaceService = dataRaceService;
//        this.diagnosticMonitor = diagnosticMonitor;
//        this.competitionNameMapper = competitionNameMapper;
//        this.competitionTypeMapper = competitionTypeMapper;
//    }
//
//    @Override
//    public void write(List<? extends List<DataRaceDTO>> dataRaceDTOList) {
//        if (dataRaceDTOList == null || dataRaceDTOList.isEmpty()) {
//            diagnosticMonitor.logError("Cannot save null object in find race data writer", getClass());
//            return;
//        }
//        FindRaceDataWriter dataRaceWriter = new FindRaceDataWriter(
//                competitionTypeService, competitionNameService, competitionTypeMapper,
//                competitionNameMapper ,  dataRaceService, diagnosticMonitor);
//        dataRaceWriter.write(dataRaceDTOList.get(0));
//    }
//}

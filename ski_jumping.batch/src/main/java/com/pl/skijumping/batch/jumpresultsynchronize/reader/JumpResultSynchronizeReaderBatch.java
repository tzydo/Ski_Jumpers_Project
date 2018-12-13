//package com.pl.skijumping.batch.jumpresultsynchronize.reader;
//
//import com.pl.skijumping.client.HtmlDownloader;
//import com.pl.skijumping.common.util.Pair;
//import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
//import com.pl.skijumping.service.DataRaceService;
//import org.springframework.batch.item.*;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JumpResultSynchronizeReaderBatch implements ItemStreamReader<Pair<Long, String>> {
//    private final DataRaceService dataRaceService;
//    private final String host;
//    private Iterator dataRaceIdAndHostIterator;
//    private JumpResultSynchronizeReader jumpResultSynchronizeReader;
//
//    public JumpResultSynchronizeReaderBatch(DataRaceService dataRaceService,
//                                            DiagnosticMonitor diagnosticMonitor,
//                                            HtmlDownloader htmlDownloader,
//                                            @Value("${skijumping.settings.skiJumpingResultsHost}") String host) {
//        this.dataRaceService = dataRaceService;
//        this.host = host;
//        this.jumpResultSynchronizeReader = new JumpResultSynchronizeReader(diagnosticMonitor, htmlDownloader);
//    }
//
//    @Override
//    public Pair<Long, String> read() throws Exception {
//        if (dataRaceIdAndHostIterator != null && dataRaceIdAndHostIterator.hasNext()) {
//            Pair<Long, String> raceData = (Pair<Long, String>) dataRaceIdAndHostIterator.next();
//            String resultData = jumpResultSynchronizeReader.getResultData(raceData.getRight());
//            return new Pair<>(raceData.getLeft(), resultData);
//        }
//        return null;
//    }
//
//    @Override
//    public void open(ExecutionContext executionContext) throws ItemStreamException {
//        List<Long> raceDataIds = dataRaceService.getRaceDataIds();
//        if (!raceDataIds.isEmpty()) {
//            List<Pair<Long, String>> raceData = raceDataIds.stream()
//                    .map(raceDataId -> new Pair<>(raceDataId, host + raceDataId))
//                    .collect(Collectors.toList());
//
//            dataRaceIdAndHostIterator = raceData.iterator();
//        }
//        return ;
//    }
//
//    @Override
//    public void update(ExecutionContext executionContext) throws ItemStreamException {
//        //
//    }
//
//    @Override
//    public void close() throws ItemStreamException {
//        //
//    }
//}

package com.pl.skijumping.batch.resultsynchronize.reader;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.DataRaceService;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;
import java.util.stream.Collectors;

public class ResultSynchronizeReaderBatch implements ItemStreamReader<String> {
    private final DataRaceService dataRaceService;
    private final DiagnosticMonitor diagnosticMonitor;
    private final String host;
    ListItemReader<String> listItemReader;
    private ResultSynchronizeReader resultSynchronizeReader;
    
    public ResultSynchronizeReaderBatch(DataRaceService dataRaceService,
                                        DiagnosticMonitor diagnosticMonitor,
                                        //ToDo poprawki dodac scheduler dla hosta - down
                                        String host) {
        this.dataRaceService = dataRaceService;
        this.diagnosticMonitor = diagnosticMonitor;
        this.host = host;
        this.resultSynchronizeReader = new ResultSynchronizeReader(diagnosticMonitor);
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(listItemReader == null) {
            return null;
        }
        return this.resultSynchronizeReader.getResultData(listItemReader.read());
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        List<Long> raceDataIds = dataRaceService.getRaceDataIds();
        if(!raceDataIds.isEmpty()) {
            List<String> raceData = raceDataIds.stream().map(v -> host + v).collect(Collectors.toList());
            listItemReader = new ListItemReader(raceData);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        //
    }

    @Override
    public void close() throws ItemStreamException {
        //
    }
}

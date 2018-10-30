package com.pl.skijumping.batch.jumpresultsynchronize.writer;

import com.pl.skijumping.common.util.Pair;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import com.pl.skijumping.service.JumpResultService;
import com.pl.skijumping.service.JumpResultToDataRaceService;
import com.pl.skijumping.service.JumpResultToSkiJumperService;
import com.pl.skijumping.service.SkiJumperService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JumpResultSynchronizeWriterBatch implements ItemWriter<Pair<Long, String>> {
    private final DiagnosticMonitor diagnosticMonitor;
    private final SkiJumperService skiJumperService;
    private final JumpResultToDataRaceService jumpResultToDataRaceService;
    private final JumpResultToSkiJumperService jumpResultToSkiJumperService;
    private final JumpResultService jumpResultService;

    public JumpResultSynchronizeWriterBatch(DiagnosticMonitor diagnosticMonitor,
                                            SkiJumperService skiJumperService,
                                            JumpResultToDataRaceService jumpResultToDataRaceService,
                                            JumpResultToSkiJumperService jumpResultToSkiJumperService,
                                            JumpResultService jumpResultService) {
        this.diagnosticMonitor = diagnosticMonitor;
        this.skiJumperService = skiJumperService;
        this.jumpResultToDataRaceService = jumpResultToDataRaceService;
        this.jumpResultToSkiJumperService = jumpResultToSkiJumperService;
        this.jumpResultService = jumpResultService;
    }

    @Override
    public void write(List<? extends Pair<Long, String>> raceDataIdAndWords) {
        JumpResultDataSynchronize jumpResultDataSynchronize = new JumpResultDataSynchronize(
                diagnosticMonitor, skiJumperService,
                raceDataIdAndWords.get(0).getLeft(),
                jumpResultToDataRaceService,
                jumpResultToSkiJumperService,
                jumpResultService);

        jumpResultDataSynchronize.transformData(raceDataIdAndWords.get(0).getRight());
    }
}

package com.pl.skijumping.batch.datasynchronize;

import com.pl.skijumping.batch.DataSynchronizeScheduler;
import com.pl.skijumping.batch.util.FileDelete;
import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FileDeleteTasklet implements Tasklet {
    private final DiagnosticMonitor diagnosticMonitor;

    public FileDeleteTasklet(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String filePath = chunkContext.getStepContext().getStepExecution()
                .getJobParameters().getString(DataSynchronizeScheduler.FILE_PATH);
        FileDelete fileDelete = new FileDelete(diagnosticMonitor, filePath, stepContribution);
        return fileDelete.delete();
    }
}
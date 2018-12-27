package com.pl.skijumping.batch.dataimportjob;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.util.stream.Collectors;

public class DataImporterListener implements StepExecutionListener {

    private final DiagnosticMonitor diagnosticMonitor;

    public DataImporterListener(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        //
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!stepExecution.getFailureExceptions().isEmpty()) {
            stepExecution.upgradeStatus(BatchStatus.FAILED);
            String errorMessage = stepExecution.getFailureExceptions().stream()
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining("\n\n"));
            diagnosticMonitor.logError("Error during Data Import job \n\n " + errorMessage, this.getClass());
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), errorMessage);
        }

        String exitStatus = stepExecution.getExitStatus().getExitCode();
        stepExecution.upgradeStatus(exitStatus.equals(ExitStatus.FAILED.getExitCode()) ? BatchStatus.FAILED : BatchStatus.COMPLETED);
        return stepExecution.getExitStatus();
    }
}

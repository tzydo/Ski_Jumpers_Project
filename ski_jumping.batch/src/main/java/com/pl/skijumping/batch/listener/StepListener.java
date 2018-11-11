package com.pl.skijumping.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.util.stream.Collectors;

public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        //
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(!stepExecution.getFailureExceptions().isEmpty()) {
            stepExecution.upgradeStatus(BatchStatus.FAILED);
            String errorMessage = stepExecution.getFailureExceptions().stream()
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining("\n\n"));
            return new ExitStatus(ExitStatus.FAILED.getExitCode(), errorMessage);
        }

        String exitStatus = stepExecution.getExitStatus().getExitCode();
        stepExecution.upgradeStatus(exitStatus.equals(ExitStatus.FAILED.getExitCode()) ? BatchStatus.FAILED : BatchStatus.COMPLETED);
        return stepExecution.getExitStatus();
    }
}

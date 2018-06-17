package com.pl.skijumping.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        //
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitStatus = stepExecution.getExitStatus().getExitCode();
        stepExecution.upgradeStatus(exitStatus.equals(ExitStatus.FAILED.getExitCode()) ? BatchStatus.FAILED : BatchStatus.COMPLETED);
        return stepExecution.getExitStatus();
    }
}

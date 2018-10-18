package com.pl.skijumping.batch.decider;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class DataImportDecider implements JobExecutionDecider {
    public static final String COMPLETE_DATA_IMPORT = "Complete_Data_import";
    public static final String ERROR_DATA_IMPORT = "Error_Data_Import";

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();
        if(exitStatus.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
            return new FlowExecutionStatus(COMPLETE_DATA_IMPORT);
        }
        return new FlowExecutionStatus(ERROR_DATA_IMPORT);
    }
}

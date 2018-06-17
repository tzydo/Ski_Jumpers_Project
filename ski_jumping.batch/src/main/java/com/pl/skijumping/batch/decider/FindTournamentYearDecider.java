package com.pl.skijumping.batch.decider;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class FindTournamentYearDecider implements JobExecutionDecider {
    public static final String COMPLETE_TOURNAMENT_YEAR_FINDING = "Complete_Tournament_Year_Finding";
    public static final String FAILED_TOURNAMENT_YEAR_FINDING = "Failed_Tournament_Year_Finding";


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        ExitStatus exitStatus = jobExecution.getExitStatus();
        if(exitStatus.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
            return new FlowExecutionStatus(COMPLETE_TOURNAMENT_YEAR_FINDING);
        }
        return new FlowExecutionStatus(FAILED_TOURNAMENT_YEAR_FINDING);
    }
}

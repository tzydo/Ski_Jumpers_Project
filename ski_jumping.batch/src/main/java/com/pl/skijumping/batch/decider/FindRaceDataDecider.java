//package com.pl.skijumping.batch.decider;
//
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.StepExecution;
//import org.springframework.batch.core.job.flow.FlowExecutionStatus;
//import org.springframework.batch.core.job.flow.JobExecutionDecider;
//
//public class FindRaceDataDecider implements JobExecutionDecider {
//    public static final String COMPLETE_FIND_RACE_DATA = "Complete_Find_Race_Data";
//    public static final String ERROR_FIND_RACE_DATA = "Error_Find_Race_Data";
//
//    @Override
//    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//        ExitStatus exitStatus = stepExecution.getExitStatus();
//        if (exitStatus.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
//            return new FlowExecutionStatus(COMPLETE_FIND_RACE_DATA);
//        }
//        return new FlowExecutionStatus(ERROR_FIND_RACE_DATA);
//
//    }
//}
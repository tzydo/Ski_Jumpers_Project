package com.pl.skijumping.rest.web;

import com.pl.skijumping.dto.BatchJobStatisticDTO;
import com.pl.skijumping.dto.JobStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import java.util.Objects;
import java.util.stream.Collectors;

class StatisticGenerator {

    private StatisticGenerator() {
//
    }

    static BatchJobStatisticDTO generate(JobExecution jobExecution) {
        BatchJobStatisticDTO batchJobStatisticDTO = new BatchJobStatisticDTO();
        if (!jobExecution.getExitStatus().getExitCode().contains(ExitStatus.COMPLETED.getExitCode()) || jobExecution.getExitStatus() == null) {
            batchJobStatisticDTO.setJobStatus(JobStatus.FAILED);
        } else {
            batchJobStatisticDTO.setJobStatus(JobStatus.COMPLETED);
        }

        batchJobStatisticDTO.setJobName(jobExecution.getJobInstance().getJobName());
        batchJobStatisticDTO.setExitStatus(jobExecution.getExitStatus().getExitDescription());
        batchJobStatisticDTO.setErrorMessage(getErrorMessage(jobExecution));

        return batchJobStatisticDTO;
    }

    private static String getErrorMessage(JobExecution jobExecution) {
        if (jobExecution != null && jobExecution.getAllFailureExceptions() != null) {
            return jobExecution.getAllFailureExceptions().stream()
                    .filter(Objects::nonNull)
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining("\n"));
        }
        return null;
    }
}
package com.pl.skijumping.rest.web;

import com.pl.skijumping.dto.BatchJobStatisticDTO;
import com.pl.skijumping.dto.JobStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import java.util.Arrays;

public class StatisticGeneratorTest {

    @Test
    public void testGenerateWhenComplete() {
        JobExecution mockJobExecution = Mockito.mock(JobExecution.class);
        Mockito.when(mockJobExecution.getJobInstance()).thenReturn(new JobInstance(1L, "testName"));
        Mockito.when(mockJobExecution.getExitStatus()).thenReturn(new ExitStatus(ExitStatus.COMPLETED.toString(), "test message"));
        BatchJobStatisticDTO generate = StatisticGenerator.generate(mockJobExecution);

        BatchJobStatisticDTO expectedBatchJobStatisticDTO = new BatchJobStatisticDTO().jobName("testName").jobStatus(JobStatus.COMPLETED).exitStatus("test message").errorMessage("");
        Assertions.assertThat(generate).isEqualToComparingFieldByField(expectedBatchJobStatisticDTO);
    }

    @Test
    public void testGenerateWhenFailed() {
        JobExecution mockJobExecution = Mockito.mock(JobExecution.class);
        Mockito.when(mockJobExecution.getJobInstance()).thenReturn(new JobInstance(1L, "testName"));
        Mockito.when(mockJobExecution.getExitStatus()).thenReturn(new ExitStatus(ExitStatus.FAILED.toString()));
        Mockito.when(mockJobExecution.getAllFailureExceptions()).thenReturn(Arrays.asList(new Throwable("error"), new Throwable("secondError")));
        BatchJobStatisticDTO generate = StatisticGenerator.generate(mockJobExecution);

        BatchJobStatisticDTO expectedBatchJobStatisticDTO = new BatchJobStatisticDTO().jobName("testName").jobStatus(JobStatus.FAILED).exitStatus("").errorMessage("error\nsecondError");
        Assertions.assertThat(generate).isEqualToComparingFieldByField(expectedBatchJobStatisticDTO);
    }
}